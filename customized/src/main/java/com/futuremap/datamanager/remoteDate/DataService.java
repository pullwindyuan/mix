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
                        //relation??????????????????????????????????????????????????????????????????????????????group????????????????????????
                        String group = targetField;
                        if(relation != null) {
                            group = field.getJSONObject("relation").getString("name");
                        }
                        //inlineFieldCollection????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
                        //???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
                        //?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
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

                                //TODO ?????????????????????header??????????????????inlineFieldCollection???????????????????????????????????????inlineFieldHeader???????????????
                                String header = targetFieldRule.getString("header");
                                String headerValue = el.getString(header);
                                //?????????????????????
                                //?????????????????????????????????inline??????????????????????????????????????????????????????????????????;
                                JSONObject allFieldData = JSONObject.parseObject(sourceObj.toJSONString());
                                allFieldData.putAll(el);
                                preprocessAll(KV, allFieldData, targetObj);
                                //?????????????????????????????????header???????????????key?????????
                                finalInlineFieldCollection.put(headerValue, el);
                            });
                        }else {
                            targetObj.put(targetField, value);
                            //?????????
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
            //??????????????????
            FIELD_OUT.forEach((targetFieldName, targetFieldValue)->{
                //sourceObj.put(targetFieldName, targetFieldValue);
                targetObj.put(targetFieldName, targetFieldValue);
            });
        }
        if(TABLE_OUT != null) {
            //??????????????????????????????
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
                    //????????????
                    if(findResult == null) {
                        mongoTemplate.insert(sourceObj, TABLE_OUT_TO);
                    }
                }else {
                    //????????????
                    JSONObject outPut = new JSONObject();
                    TABLE_OUT_MAP.forEach((targetFieldName, sourceFieldName)->{
                        String value = sourceObj.getString((String) sourceFieldName);
                        //????????????????????????????????????????????????????????????????????????
                        if(value == null) {
                            value = (String) sourceFieldName;
                        }
                        outPut.put(targetFieldName, value);
                    });
                    //TODO ???????????????????????????????????????????????????
                    if(findResult == null) {
                        mongoTemplate.insert(outPut, TABLE_OUT_TO);
                    }
                }
            });
        }
    }

    public void preprocess(JSONObject KV, JSONObject sourceObj, String fieldName, Object sourceValue, JSONObject targetObj) {
        //?????????????????????
        JSONObject preprocess = KV.getJSONObject("preprocess");
        if(preprocess != null) {
            //????????????????????????
            JSONObject preprocessRule = preprocess.getJSONObject("kv");
            //?????????????????????????????????????????????;
            JSONArray fieldPreprocessorArray = preprocessRule.getJSONArray(fieldName);
            if(fieldPreprocessorArray != null) {
                List<JSONObject> fieldPreprocessorList = fieldPreprocessorArray.toJavaList(JSONObject.class);
                for (JSONObject currFieldPreprocessor : fieldPreprocessorList) {
                    JSONObject currFieldPreprocessRule = currFieldPreprocessor.getJSONObject("rule");
                    String preprocessType = currFieldPreprocessor.getString("type");
                    if ("FIELD_SPLIT".equals(preprocessType)) {
                        //????????????
                        JSONObject dataBaseIO = currFieldPreprocessRule.getJSONObject("DATABASE_IO");
                        String IoType = dataBaseIO.getString("TYPE");
                        JSONObject IoMap = dataBaseIO.getJSONObject("MAP");
                        IoMap.forEach((method, io) -> {
                            if ("FIELD".equals(IoType)) {
                                //TODO ???????????????????????????????????????????????????
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
                                            //TODO ???????????????????????????????????????????????????????????????
                                            if(RIGHT_VALUE.equals(LEFT_VALUE)) {
                                                processFieldSplitJudgment(IF, sourceObj,  targetObj, "TRUE_FIELD_OUT", "TRUE_TABLE_OUT");
                                            }else {
                                                processFieldSplitJudgment(IF, sourceObj, targetObj, "FALSE_FIELD_OUT", "FALSE_TABLE_OUT");
                                            }
                                        }else {
                                            //TODO ????????????????????????
                                        }
                                    } else if ("FIELD_COPY".equals(method)) {
                                        //??????????????????????????????????????????????????????????????????????????????
                                        targetObj.put(OUT, sourceValue);
                                    }
                                });
                            }
                        });
                    } else if ("FIELD_ARITHMETIC".equals(preprocessType)) {
                        //??????????????????
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

                            //????????????????????????
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
                            //????????????????????????
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
                        //??????????????????
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
                                //??????????????????????????????????????????????????????null?????????????????????????????????????????????
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
                                            //??????????????????????????????????????????????????????????????????????????????,?????????????????????
                                            mongoTemplate.insert(toRightObj, rightFrom);
                                        } else {
                                            //?????????????????????????????????
                                            mongoTemplate.findAndReplace(new Query(Criteria.where(rightName).is(leftValue)), toRightObj, rightFrom);
                                        }
                                        //
                                    } else {

                                    }
                                });
                            }
                        });
                    } else if ("TABLE_SPLIT".equals(preprocessType)) {
                        //?????????????????????
                        JSONObject dataBaseIO = currFieldPreprocessRule.getJSONObject("DATABASE_IO");
                        String IoType = dataBaseIO.getString("TYPE");
                        JSONObject IoMap = dataBaseIO.getJSONObject("MAP");
                        IoMap.forEach((method, io) -> {
                            if ("FIELD".equals(IoType)) {
                                //TODO ???????????????????????????????????????????????????
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
                                                    //TODO ???????????????
                                                }
                                            });
                                        }
                                        JSONObject find;
                                        //if(OUT_DEFAULT.get(TO_MATCH_FIELD) != null)????????????????????????????????????????????????????????????null?????????????????????????????????,????????????null????????????????????????????????????????????????????????????????????????
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
                                                //TODO ???????????????
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
                        //??????????????????
                        JSONObject dataBaseIO = currFieldPreprocessRule.getJSONObject("DATABASE_IO");
                        String IoType = dataBaseIO.getString("TYPE");
                        JSONObject IoMap = dataBaseIO.getJSONObject("MAP");
                        IoMap.forEach((method, io) -> {
                            if ("FIELD".equals(IoType)) {
                                //TODO ???????????????????????????????????????????????????
                                List ioList = (List) io;
                                ioList.forEach(e -> {
                                    if ("STRING_TO_DOUBLE".equals(method)) {
                                        String TO = (String) ((LinkedHashMap) e).get("TO");
                                        String OUT = (String) ((LinkedHashMap) e).get("OUT");
                                        if ("TARGET_OBJ".equals(TO)) {
                                            targetObj.put(OUT, new BigDecimal((String) sourceValue).doubleValue());
                                        } else {
                                            //TODO ???????????????

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
                //??????????????????
                BY.forEach((item)->{
                    item.forEach((by, targetName)->{
                        String[] groupBy = by.split("-");
                        List<String> groupList = new ArrayList<>();
                        List<String> projectList = new ArrayList<>();
                        for (String s : groupBy) {
                            groupList.add(s);
                            projectList.add(s);
                        }

                        //????????????????????????
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
                //????????????????????????????????????cInvCode????????????????????????????????????????????????????????????plate tradType??????????????????????????????

                //???????????????????????????????????????????????????????????????????????????
                //??????????????????????????????????????????????????????
            }
        });
    }
}
