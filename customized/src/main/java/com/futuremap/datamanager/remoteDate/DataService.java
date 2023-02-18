package com.futuremap.datamanager.remoteDate;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.futuremap.base.util.DateUtils;
import com.futuremap.base.util.HttpClient;
import com.futuremap.datamanager.bean.DTO.ProcessDTO;
import com.futuremap.datamanager.dataProcess.Aggregate;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class DataService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private Aggregate aggregate;

    public  void initData(String dataCode, JSONObject paramsData) {
        JSONObject api = mongoTemplate.findOne(new Query(Criteria.where("id").is(dataCode)), JSONObject.class, "api");
        Map<String, String> params = new HashMap<>();
        long total = 0;
        params.put("data", paramsData.toJSONString());
        String response = HttpClient.post(params, api.getString("value"));
        String jsonString = StringEscapeUtils.unescapeJava(response);
        jsonString = jsonString.replace("\"{", "{");
        jsonString = jsonString.replace("}\"", "}");
        JSONObject result = JSONObject.parseObject(jsonString);
        if(result.getBoolean("status")) {
            List<JSONObject> countList = result.getJSONObject("Data").getJSONArray("Count").toJavaList(JSONObject.class);
            total = countList.get(0).getLong("count");
        }
        int pageNo = 1;
        paramsData.put("pagesize", 100);
        JSONObject userTemplate = mongoTemplate.findOne(new Query(Criteria.where("id").is(dataCode)), JSONObject.class, "user_template");
        JSONObject KV = null;
        if(userTemplate != null) {
            KV = userTemplate.getJSONObject("kv");
        }
        for(long count = 0; count < total; ) {
            paramsData.put("pageno", pageNo);
            params.put("data", paramsData.toJSONString());
            response = HttpClient.post(params, api.getString("value"));
            jsonString = StringEscapeUtils.unescapeJava(response);
            jsonString = jsonString.replace("\"{", "{");
            jsonString = jsonString.replace("}\"", "}");
            result = JSONObject.parseObject(jsonString);

            if(result.getBoolean("status")) {
                JSONArray data = result.getJSONObject("Data").getJSONArray("Data");
                processData(KV, data);
                count += data.size();
                pageNo ++;
            }
        }
        additionalProcess(KV);
    }

    public void processDataByCode(String apiCode, JSONArray data) {
        JSONObject userTemplate = mongoTemplate.findOne(new Query(Criteria.where("id").is(apiCode)), JSONObject.class, "user_template");
        if(userTemplate == null) {
            return;
        }
        JSONObject KV = userTemplate.getJSONObject("kv");
        processData(KV, data);
    }

    public void processData(JSONObject KV, JSONArray data) {
        String collectionName;
        collectionName = KV.getJSONObject("source").getString("value");
        if(KV != null) {
            JSONArray formatData = new JSONArray();
            data.forEach((item)->{
                JSONObject sourceObj = (JSONObject) item;
                JSONObject targetObj = new JSONObject();
                sourceObj.forEach((key, value)->{
                    JSONObject field = KV.getJSONObject(key);
                    if(field != null) {
                        String targetField = field.getJSONObject("columnName").getString("value");
                        JSONObject relation = field.getJSONObject("relation");
                        //relation为空表示本字段没有指定分组属性，所以默认就是独立列，group就是目标字段本身
                        String group = targetField;
                        if(relation != null) {
                            group = field.getJSONObject("relation").getString("name");
                        }
                        //inlineFieldCollection最大的用处在于可以进行行列转置，根据实际情况可以将行唯一标识数据转换成列字段，将列字段数据转成行标识，反之亦然。
                        //行列转置的关键在于如何找哼或者列标识。比如在预算管理当中，我们就将月份行标识转置成了列字段。而把列字段的销量、销售额、单价、成本等转置成立行标识。
                        //可以看出我们都是针对序列属性的字段进行入手。比如在销售发货列表中我们就把子表行号最为序列字段保持在行，列字段任然不变，也就是没有进行行列转置。
                        if("inlineFieldCollection".equals(group)) {
                            JSONObject targetFieldRule = field.getJSONObject("columnName").getJSONObject("rule");
                            JSONObject inlineFieldCollection = targetObj.getJSONObject(targetField);

                            if(inlineFieldCollection == null) {
                                inlineFieldCollection = new JSONObject();
                                targetObj.put(targetField, inlineFieldCollection);
                            }
                            JSONArray collection = (JSONArray) value;
                            JSONObject finalInlineFieldCollection = inlineFieldCollection;
                            collection.forEach(e->{
                                JSONObject el = (JSONObject) e;

                                //TODO 要首先找到这个header，可以通过在inlineFieldCollection对应的字段配置中预先设置好inlineFieldHeader对应的字段
                                String header = targetFieldRule.getString("header");
                                String headerValue = el.getString(header);
                                //遍历进行预处理
                                //传入全量数据，不然只传inline数据或导致数据处理的时候有的配置的字段找不到;
                                JSONObject allFieldData = JSONObject.parseObject(sourceObj.toJSONString());
                                allFieldData.putAll(el);
                                preprocessAll(KV, allFieldData, targetObj);
                                //将行内扩展的信息直接以header字段的值为key来存储
                                finalInlineFieldCollection.put(headerValue, el);
                            });
                        }else {
                            targetObj.put(targetField, value);
                            //预处理
                            JSONObject sourceCopy = JSONObject.parseObject(sourceObj.toJSONString());
                            preprocess(KV, sourceCopy, key, value, targetObj);
                        }
                    }
                });
                formatData.add(targetObj);
            });
            mongoTemplate.insert(formatData, collectionName);
        }else {
            mongoTemplate.insert(data, collectionName);
        }
    }

    public void preprocessAll(JSONObject KV, JSONObject sourceObj, JSONObject targetObj) {
        JSONObject sourceCopy = JSONObject.parseObject(sourceObj.toJSONString());
        sourceCopy.forEach((k, v)->{
            preprocess(KV, sourceObj, k, v, targetObj);
        });
    }

    private void processFieldSplitJudgment(JSONObject rule, JSONObject sourceObj, JSONObject targetObj, String fieldOut, String tableOut) {
        JSONObject FIELD_OUT = rule.getJSONObject(fieldOut);
        List<JSONObject>  TABLE_OUT = rule.getJSONArray(tableOut).toJavaList(JSONObject.class);

        if(FIELD_OUT != null) {
            //表内字段拆分
            FIELD_OUT.forEach((targetFieldName, targetFieldValue)->{
                //sourceObj.put(targetFieldName, targetFieldValue);
                targetObj.put(targetFieldName, targetFieldValue);
            });
        }
        if(TABLE_OUT != null) {
            //表内字段拆分到其他表
            TABLE_OUT.forEach(tableOutRule->{
                String TABLE_OUT_TO = tableOutRule.getString("TO");
                String OUT_TYPE = tableOutRule.getString("OUT_TYPE");
                String PRIMARY_KEY = tableOutRule.getString("PRIMARY_KEY");
                JSONObject TABLE_OUT_MAP = tableOutRule.getJSONObject("OUT");
                if(!mongoTemplate.collectionExists(TABLE_OUT_TO)) {
                    mongoTemplate.createCollection(TABLE_OUT_TO);
                }
                JSONObject findResult = mongoTemplate.findOne(new Query(Criteria.where(PRIMARY_KEY).is(sourceObj.getString(PRIMARY_KEY))), JSONObject.class, TABLE_OUT_TO);
                if("ALL".equals(OUT_TYPE)) {
                    //整行输出
                    if(findResult == null) {
                        mongoTemplate.insert(sourceObj, TABLE_OUT_TO);
                    }
                }else {
                    //过滤输出
                    JSONObject outPut = new JSONObject();
                    TABLE_OUT_MAP.forEach((targetFieldName, sourceFieldName)->{
                        String value = sourceObj.getString((String) sourceFieldName);
                        //如果输入数据中没有对应字段，就直接使用配置中的值
                        if(value == null) {
                            value = (String) sourceFieldName;
                        }
                        outPut.put(targetFieldName, value);
                    });
                    //TODO 后期需要优化成异步批量保存到数据库
                    if(findResult == null) {
                        mongoTemplate.insert(outPut, TABLE_OUT_TO);
                    }
                }
            });
        }
    }

    public void preprocess(JSONObject KV, JSONObject sourceObj, String fieldName, Object sourceValue, JSONObject targetObj) {
        //获取预处理规则
        JSONObject preprocess = KV.getJSONObject("preprocess");
        if(preprocess != null) {
            //获取规则详细内容
            JSONObject preprocessRule = preprocess.getJSONObject("kv");
            //查找当前字段是否需要进行预处理;
            JSONArray fieldPreprocessorArray = preprocessRule.getJSONArray(fieldName);
            if(fieldPreprocessorArray != null) {
                List<JSONObject> fieldPreprocessorList = fieldPreprocessorArray.toJavaList(JSONObject.class);
                for (JSONObject currFieldPreprocessor : fieldPreprocessorList) {
                    JSONObject currFieldPreprocessRule = currFieldPreprocessor.getJSONObject("rule");
                    String preprocessType = currFieldPreprocessor.getString("type");
                    if ("FIELD_SPLIT".equals(preprocessType)) {
                        //字段拆分
                        JSONObject dataBaseIO = currFieldPreprocessRule.getJSONObject("DATABASE_IO");
                        String IoType = dataBaseIO.getString("TYPE");
                        JSONObject IoMap = dataBaseIO.getJSONObject("MAP");
                        IoMap.forEach((method, io) -> {
                            if ("FIELD".equals(IoType)) {
                                //TODO 后面需要把日历处理做成统一的工具类
                                List<JSONObject> ioList = IoMap.getJSONArray(method).toJavaList(JSONObject.class);
                                ioList.forEach(e -> {
                                    String OUT = e.getString("OUT");
                                    if ("CALENDAR_DAY".equals(method)) {
                                        String yearMonthDay = DateUtils.getYearMonthDayString((String) sourceValue);
                                        targetObj.put(OUT, yearMonthDay);
                                    } else if ("CALENDAR_MONTH".equals(method)) {
                                        String yearMonth = DateUtils.getYearMonthString((String) sourceValue);
                                        targetObj.put(OUT, yearMonth);
                                    } else if ("CALENDAR_YEAR".equals(method)) {
                                        String year = DateUtils.getYearString((String) sourceValue);
                                        targetObj.put(OUT, year);
                                    } else if ("CONDITION_SPLIT".equals(method)) {
                                        JSONObject IF = e.getJSONObject("IF");
                                        String JUDGMENT = IF.getString("JUDGMENT");
                                        String LEFT_FIELD = IF.getString("LEFT_FIELD");
                                        String RIGHT_VALUE = IF.getString("RIGHT_VALUE");
                                        if ("EQUALS".equals(JUDGMENT)) {
                                            String LEFT_VALUE = sourceObj.getString(LEFT_FIELD);
                                            //TODO 多个判断条件的通过嵌套来实现，这里先不实现
                                            if(RIGHT_VALUE.equals(LEFT_VALUE)) {
                                                processFieldSplitJudgment(IF, sourceObj,  targetObj, "TRUE_FIELD_OUT", "TRUE_TABLE_OUT");
                                            }else {
                                                processFieldSplitJudgment(IF, sourceObj, targetObj, "FALSE_FIELD_OUT", "FALSE_TABLE_OUT");
                                            }
                                        }else {
                                            //TODO 其他情况按需增加
                                        }
                                    } else if ("FIELD_COPY".equals(method)) {
                                        //表内字段拷贝，都是从子表拷贝到主表，其他情况没有意义
                                        targetObj.put(OUT, sourceValue);
                                    }
                                });
                            }
                        });
                    } else if ("FIELD_ARITHMETIC".equals(preprocessType)) {
                        //字段算术运算
                        currFieldPreprocessRule.forEach((key, value) -> {
                            JSONObject ARITHMETIC = currFieldPreprocessRule.getJSONObject(key);
                            JSONObject INPUT = ARITHMETIC.getJSONObject("INPUT");
                            JSONObject EQUALS = ARITHMETIC.getJSONObject("EQUALS");
                            String OPERATION = ARITHMETIC.getString("OPERATION");

                            JSONObject FIRST = INPUT.getJSONObject("FIRST");
                            JSONObject SECOND = INPUT.getJSONObject("SECOND");
                            String firstFrom = FIRST.getString("FROM");
                            String secondFrom = SECOND.getString("FROM");

                            String firstName = FIRST.getString("NAME");
                            String secondName = SECOND.getString("NAME");

                            BigDecimal firstInputValue = BigDecimal.ZERO;
                            BigDecimal secondInputValue = BigDecimal.ZERO;

                            String resultTo = EQUALS.getString("TO");
                            String resultFormat = EQUALS.getString("FORMAT");
                            String resultName = EQUALS.getString("NAME");
                            BigDecimal resultValue = BigDecimal.ZERO;

                            //获取第一个操作数
                            if ("TARGET_OBJ".equals(firstFrom)) {
                                String targetStr = targetObj.getString(firstName);
                                if (StringUtils.isNotBlank(targetStr)) {
                                    firstInputValue = new BigDecimal(targetStr);
                                }
                            } else {
                                String sourceStr = sourceObj.getString(firstName);
                                if (StringUtils.isNotBlank(sourceStr)) {
                                    firstInputValue = new BigDecimal(sourceStr);
                                }
                            }
                            //获取第二个操作数
                            if ("TARGET_OBJ".equals(secondFrom)) {
                                String targetStr = targetObj.getString(secondName);
                                if (StringUtils.isNotBlank(targetStr)) {
                                    secondInputValue = new BigDecimal(targetStr);
                                }
                            } else {
                                String sourceStr = sourceObj.getString(secondName);
                                if (StringUtils.isNotBlank(sourceStr)) {
                                    secondInputValue = new BigDecimal(sourceStr);
                                }
                            }
                            if ("ADD".equals(OPERATION)) {
                                resultValue = firstInputValue.add(secondInputValue);
                            } else if ("SUB".equals(OPERATION)) {
                                resultValue = firstInputValue.subtract(secondInputValue);
                            } else if ("MULTI".equals(OPERATION)) {
                                resultValue = firstInputValue.multiply(secondInputValue);
                            } else if ("DIV".equals(OPERATION)) {
                                resultValue = firstInputValue.divide(secondInputValue);
                            }

                            if ("DOUBLE".equals(resultFormat)) {
                                if ("TARGET_OBJ".equals(resultTo)) {
                                    targetObj.put(resultName, resultValue.doubleValue());
                                } else {

                                }
                            } else if ("LONG".equals(resultFormat)) {
                                if ("TARGET_OBJ".equals(resultTo)) {
                                    targetObj.put(resultName, resultValue.longValue());
                                } else {

                                }
                            } else {
                                if ("TARGET_OBJ".equals(resultTo)) {
                                    targetObj.put(resultName, resultValue.intValue());
                                } else {

                                }
                            }
                        });

                    } else if ("JOIN".equals(preprocessType)) {
                        //联表处理数据
                        currFieldPreprocessRule.forEach((key, value) -> {
                            JSONObject JOIN = currFieldPreprocessRule.getJSONObject(key);
                            JSONObject RIGHT = JOIN.getJSONObject("RIGHT");
                            JSONObject MATCH = JOIN.getJSONObject("MATCH");
                            List<JSONObject> OUT_RIGHT = JOIN.getJSONArray("OUT_RIGHT").toJavaList(JSONObject.class);
                            List<JSONObject> OUT_LEFT = JOIN.getJSONArray("OUT_RIGHT").toJavaList(JSONObject.class);
                            JSONObject OUT_RIGHT_DEFAULT = JOIN.getJSONObject("OUT_RIGHT_DEFAULT");
                            JSONObject OUT_LEFT_DEFAULT = JOIN.getJSONObject("OUT_LEFT_DEFAULT");
                            String JUDGMENT = MATCH.getString("JUDGMENT");
                            String RESULT = MATCH.getString("RESULT");

                            String rightFrom = RIGHT.getString("FROM");

                            String leftName = fieldName;
                            String rightName = RIGHT.getString("NAME");

                            String leftValue = (String) sourceValue;
                            JSONObject rightResultObj = null;
                            if ("UNIQUE".equals(RESULT)) {
                                if ("EQUALS".equals(JUDGMENT)) {
                                    rightResultObj = mongoTemplate.findOne(new Query(Criteria.where(rightName).is(leftValue)), JSONObject.class, rightFrom);
                                }else {

                                }
                            }

                            JSONObject finalRightResultObj = rightResultObj;
                            JSONObject toRightObj = new JSONObject();
                            if(OUT_RIGHT != null) {
                                //左侧到右侧是写表，如果左侧输入值不为null，就使用左侧值。默认使用预设值
                                OUT_RIGHT.forEach(e -> {
                                    JSONObject outMap = e;
                                    outMap.forEach((targetFieldName, sourceFieldName)->{
                                        Object toDefaultValue = null;
                                        if(OUT_RIGHT_DEFAULT != null) {
                                            toDefaultValue = OUT_RIGHT_DEFAULT.get(targetFieldName);
                                        }
                                        String toValueStr = sourceObj.getString((String) sourceFieldName);
                                        Object toValue = sourceObj.get(sourceFieldName);
                                        if(StringUtils.isBlank(toValueStr)) {
                                            toValue = toDefaultValue;
                                        }
                                        toRightObj.put(targetFieldName, toValue);
                                    });
                                    if ("UNIQUE".equals(RESULT)) {
                                        if (finalRightResultObj == null) {
                                            //在唯一的限制条件下数据存在就不处理，不考虑更新的情况,否者效率非常低
                                            mongoTemplate.insert(toRightObj, rightFrom);
                                        } else {
                                            //如果数据存在就更新数据
                                            mongoTemplate.findAndReplace(new Query(Criteria.where(rightName).is(leftValue)), toRightObj, rightFrom);
                                        }
                                        //
                                    } else {

                                    }
                                });
                            }
                        });
                    } else if ("TABLE_SPLIT".equals(preprocessType)) {
                        //表拆分或者派生
                        JSONObject dataBaseIO = currFieldPreprocessRule.getJSONObject("DATABASE_IO");
                        String IoType = dataBaseIO.getString("TYPE");
                        JSONObject IoMap = dataBaseIO.getJSONObject("MAP");
                        IoMap.forEach((method, io) -> {
                            if ("FIELD".equals(IoType)) {
                                //TODO 后面需要把日历处理做成统一的工具类
                                List ioList = (List) io;
                                ioList.forEach(e -> {
                                    if ("CALENDAR_DAY".equals(method)) {
                                        String OUT = (String) ((LinkedHashMap) e).get("OUT");
                                        String yearMonthDay = DateUtils.getYearMonthDayString((String) sourceValue);
                                        targetObj.put(OUT, yearMonthDay);
                                    } else if ("CALENDAR_MONTH".equals(method)) {
                                        String OUT = (String) ((LinkedHashMap) e).get("OUT");
                                        String yearMonth = DateUtils.getYearMonthString((String) sourceValue);
                                        targetObj.put(OUT, yearMonth);
                                    } else if ("CALENDAR_YEAR".equals(method)) {
                                        String OUT = (String) ((LinkedHashMap) e).get("OUT");
                                        String year = DateUtils.getYearString((String) sourceValue);
                                        targetObj.put(OUT, year);
                                    } else if ("FIELD_COPY".equals(method)) {
                                        String FROM = (String) ((LinkedHashMap) e).get("FROM");
                                        String FROM_MATCH_FIELD = (String) ((LinkedHashMap) e).get("FROM_MATCH_FIELD");
                                        String TO = (String) ((LinkedHashMap) e).get("TO");
                                        String MATCH = (String) ((LinkedHashMap) e).get("MATCH");
                                        String TO_MATCH_FIELD = (String) ((LinkedHashMap) e).get("TO_MATCH_FIELD");
                                        LinkedHashMap OUT = (LinkedHashMap) ((LinkedHashMap) e).get("OUT");
                                        LinkedHashMap OUT_DEFAULT = (LinkedHashMap) ((LinkedHashMap) e).get("OUT_DEFAULT");
                                        LinkedHashMap OUT_INLINECHILDREN = (LinkedHashMap) ((LinkedHashMap) e).get("OUT_INLINECHILDREN");
                                        Document copyObj = new Document();
                                        if(OUT != null) {
                                            OUT.forEach((targetFieldName, sourceFieldName) -> {
                                                if ("SOURCE_OBJ".equals(FROM)) {
                                                    if(sourceValue == null) {
                                                        copyObj.put((String) targetFieldName, OUT_DEFAULT.get(targetFieldName));
                                                    }else {
                                                        copyObj.put((String) targetFieldName, sourceValue);
                                                    }
                                                } else {
                                                    //TODO 其他先不做
                                                }
                                            });
                                        }
                                        JSONObject find;
                                        //if(OUT_DEFAULT.get(TO_MATCH_FIELD) != null)表示如果目标输出的字段有默认值（当源值为null的时候使用默认值替代）,当源值为null的时候就需要使用默认值去目标表中查找是否存在记录
                                        if(OUT_DEFAULT.get(TO_MATCH_FIELD) != null) {
                                            if(sourceValue  == null) {
                                                find = mongoTemplate.findOne(new Query(Criteria.where(TO_MATCH_FIELD).is(OUT_DEFAULT.get(TO_MATCH_FIELD))), JSONObject.class, TO);
                                            }else {
                                                find = mongoTemplate.findOne(new Query(Criteria.where(TO_MATCH_FIELD).is(sourceValue)), JSONObject.class, TO);
                                            }
                                        }else {
                                            find = mongoTemplate.findOne(new Query(Criteria.where(TO_MATCH_FIELD).is(sourceObj.getString(FROM_MATCH_FIELD))), JSONObject.class, TO);
                                        }

                                        JSONObject inlineChildren = null;
                                        if(OUT_INLINECHILDREN != null) {
                                            if ("SOURCE_OBJ".equals(FROM)) {
                                                String header = (String) OUT_INLINECHILDREN.get("header");
                                                if(find != null) {
                                                    inlineChildren = find.getJSONObject("inlineChildren");
                                                    inlineChildren.put(sourceObj.getString(header), sourceObj);
                                                    find.put("inlineChildren", inlineChildren);
                                                }else {
                                                    inlineChildren = new JSONObject();
                                                    inlineChildren.put(sourceObj.getString(header), sourceObj);
                                                    copyObj.put("inlineChildren", inlineChildren);
                                                }

                                            } else {
                                                //TODO 其他先不做
                                            }
                                        }
                                        if ("UNIQUE".equals(MATCH)) {
                                            if (find == null) {
                                                mongoTemplate.insert(copyObj, TO);
                                            }else {
                                                Update update = Update.update("inlineChildren", inlineChildren);
                                                mongoTemplate.findAndModify(new Query(Criteria.where(TO_MATCH_FIELD).is(sourceValue)), update, JSONObject.class, TO);
                                            }
                                            //
                                        } else {

                                        }
                                    }
                                });
                            }
                        });
                    } else if ("FORMAT_TRANSFORM".equals(preprocessType)) {
                        //字段格式转换
                        JSONObject dataBaseIO = currFieldPreprocessRule.getJSONObject("DATABASE_IO");
                        String IoType = dataBaseIO.getString("TYPE");
                        JSONObject IoMap = dataBaseIO.getJSONObject("MAP");
                        IoMap.forEach((method, io) -> {
                            if ("FIELD".equals(IoType)) {
                                //TODO 后面需要把日历处理做成统一的工具类
                                List ioList = (List) io;
                                ioList.forEach(e -> {
                                    if ("STRING_TO_DOUBLE".equals(method)) {
                                        String TO = (String) ((LinkedHashMap) e).get("TO");
                                        String OUT = (String) ((LinkedHashMap) e).get("OUT");
                                        if ("TARGET_OBJ".equals(TO)) {
                                            targetObj.put(OUT, new BigDecimal((String) sourceValue).doubleValue());
                                        } else {
                                            //TODO 其他先不做

                                        }
                                    }
                                });
                            }
                        });
                    }
                }
            }
        }
    }

    public void additionalProcess(JSONObject KV) {
        ProcessDTO processDTO = new ProcessDTO();
        JSONObject additionalProcess = KV.getJSONObject("additionalProcess");
        if(additionalProcess == null) {
            return;
        }
        processDTO.setQueryId(additionalProcess.getString("value"));
        JSONObject processor = KV.getJSONObject("additionalProcess").getJSONObject("kv");
        processor.forEach((key, value)->{
            JSONObject processorItem = processor.getJSONObject(key);
            String type = processorItem.getString("type");
            JSONObject rule = processorItem.getJSONObject("rule").getJSONObject("DATABASE_IO");
            if("GROUP".equals(type)) {
                Map<String, Map<String, String>> aggregateMap = new HashMap<>();
                JSONObject MAP = rule.getJSONObject(        "MAP");
                List<JSONObject> BY = rule.getJSONArray("BY").toJavaList(JSONObject.class);
                //组装分组条件
                BY.forEach((item)->{
                    item.forEach((by, targetName)->{
                        String[] groupBy = by.split("-");
                        List<String> groupList = new ArrayList<>();
                        List<String> projectList = new ArrayList<>();
                        for (String s : groupBy) {
                            groupList.add(s);
                            projectList.add(s);
                        }

                        //组装聚合字段集合
                        JSONArray SUM = MAP.getJSONArray("SUM");
                        Map<String, String> sumFieldMap = new HashMap<>();
                        aggregateMap.put("SUM", sumFieldMap);
                        SUM.forEach((io)->{
                            Map<String, String> IO = (Map<String, String>) io;
                            sumFieldMap.put(IO.get("IN"), IO.get("OUT")) ;
                            projectList.add(IO.get("IN"));
                        });

                        JSONArray AVG = MAP.getJSONArray("AVG");
                        Map<String, String> avgFieldMap = new HashMap<>();
                        aggregateMap.put("AVG", avgFieldMap);
                        AVG.forEach((io)->{
                            Map<String, String> IO = (Map<String, String>) io;
                            avgFieldMap.put(IO.get("IN"), IO.get("OUT")) ;
                        });

                        JSONArray MAX = MAP.getJSONArray("AVG");
                        Map<String, String> maxFieldMap = new HashMap<>();
                        aggregateMap.put("MAX", maxFieldMap);
                        MAX.forEach((io)->{
                            Map<String, String> IO = (Map<String, String>) io;
                            maxFieldMap.put(IO.get("IN"), IO.get("OUT")) ;
                        });

                        JSONArray MIN = MAP.getJSONArray("AVG");
                        Map<String, String> minFieldMap = new HashMap<>();
                        aggregateMap.put("MIN", minFieldMap);
                        MIN.forEach((io)->{
                            Map<String, String> IO = (Map<String, String>) io;
                            minFieldMap.put(IO.get("IN"), IO.get("OUT")) ;
                        });
                        processDTO.setGroupList(groupList);
                        processDTO.setProjectList(projectList);
                        processDTO.setAggregateMap(aggregateMap);
                        List<JSONObject> result = aggregate.analyze(processDTO);
                        result.forEach(e->{
                            e.remove("_id");
                        });
                        if(!mongoTemplate.collectionExists((String) targetName)) {
                            mongoTemplate.dropCollection((String) targetName);
                            mongoTemplate.createCollection((String) targetName);
                        }
                        mongoTemplate.insert(result, (String)targetName);
                    });
                });
                //要做的事情：将发货单信息cInvCode字段预处理到总表中，分组聚合配置中的字段plate tradType等字段目前还没有信息

                //安徽华恒、秦皇岛华恒、巴彦淖尔华恒、南阳沣益排除掉
                //内销对应人民币订单，外销对应外币订单
            }
        });
    }
}
