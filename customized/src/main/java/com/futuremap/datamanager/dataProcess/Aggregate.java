package com.futuremap.datamanager.dataProcess;

import com.alibaba.fastjson.JSONObject;
import com.futuremap.base.util.DateUtils;
import com.futuremap.datamanager.bean.DTO.ProcessDTO;
import com.futuremap.datamanager.bean.DTO.QueryDTO;
import com.futuremap.datamanager.enums.DataFormatEnum;
import com.futuremap.datamanager.enums.DataProcessDimensionSourceEnum;
import com.futuremap.datamanager.enums.DataProcessEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

import static com.futuremap.datamanager.enums.DataProcessEnum.*;

/**
 * 对数据进行自动的多维度统计分值
 * @author Pullwind
 */
@Service
@Slf4j
public class Aggregate {
    @Autowired
    private MongoTemplate mongoTemplate;

    private JSONObject findDataStructureTemplateById(String id) {
        return mongoTemplate.findById(id, JSONObject.class, "user_template");
    }

    /**
     *  各维度独立分组查询和分析
     * @param queryDTO：查询参数格式是json，参数组装规则：
     *                 {
     *                       条件分组名称1：{查询条件字段名称1：查询条件值，……  ，查询条件字段名称n：查询条件值}，
     *                                ……
     *                       条件分组名称n：{查询条件字段名称1：查询条件值，……  ，查询条件字段名称n：查询条件值}
     *                 }
     * @return
     */
    public JSONObject independentAnalyze(QueryDTO queryDTO) {
        JSONObject dataStructure =  findDataStructureTemplateById(queryDTO.getQueryId());
        String dataQueryForestStr = mongoTemplate.findOne(new Query(Criteria.where("id").is(queryDTO.getQueryId())), String.class, "user_data_tree");

        JSONObject kv = dataStructure.getJSONObject("kv");
        JSONObject aggregate = kv.getJSONObject(DataProcessEnum.AGGREGATE.name());
        String source = kv.getJSONObject("source").getString("value");

        JSONObject extra = JSONObject.parseObject(dataQueryForestStr).getJSONObject("extra");
        JSONObject queryMap = extra.getJSONObject("queryMap");

        JSONObject dataFieldChildMap = extra.getJSONObject("dataFieldChild");

        //封装查询条件，查询条件根据筛选分组进行独立查询，比如有组织类、分类属性这个两个大的查询分组条件，
        //那么就需要针对两组条件进行隔离查询，也就是在进行组织类筛选的时候不匹配分类属性，反之亦然。
        //如果分组参数为空，就需要根据每个分组的首列进行分组统计,为空就是等于“全部”
        JSONObject queryGroupMap = queryDTO.getQueryMap();
        //进行分组统计
        JSONObject groupResult = new JSONObject();

        queryMap.forEach((g, item)->{
            JSONObject queryValueMap = queryGroupMap.getJSONObject(g);

            List<AggregationOperation> operations = new ArrayList<>();

            List<String> aggregateFieldList = new ArrayList<>();
            aggregate.forEach((k, v) -> {
                aggregateFieldList.add(k);
            });

            AtomicReference<GroupOperation> groupOperation = new AtomicReference<>();
            AtomicReference<JSONObject> groupReference = new AtomicReference<>();
            groupReference.set(new JSONObject());
            operations.add(Aggregation.match(Criteria.where("templateId").is(queryDTO.getQueryId())));

            AtomicReference<ProjectionOperation> projectionOperation = new AtomicReference<>();
            //operations.add(Aggregation.sort(Sort.by(new Sort.Order(Sort.Direction.DESC, "10"))));
            aggregateFieldList.forEach(e -> {
                if (projectionOperation.get() == null) {
                    projectionOperation.set(Aggregation.project(e));
                } else {
                    projectionOperation.set(projectionOperation.get().and(e).as(e));
                }
            });

            if(queryValueMap != null) {
                //查询条件有值
//                queryValueMap.forEach((k, v) -> {
//                    if (projectionOperation.get() == null) {
//                        projectionOperation.set(Aggregation.project(k));
//                    } else {
//                        projectionOperation.set(projectionOperation.get().and(k).as(k));
//                    }
//                });

                queryValueMap.forEach((k, v) -> {
                    JSONObject columnName = kv.getJSONObject(k).getJSONObject("columnName");
                    //TODO 新版本的format放到columnName字段的rule中了
                    JSONObject formatJSONObject = kv.getJSONObject(k).getJSONObject("format");
                    String format = formatJSONObject.getString("value");
                    String rule = formatJSONObject.getString("rule");
                    //封装查询匹配条件
                    if (projectionOperation.get() == null) {
                        projectionOperation.set(Aggregation.project(k));
                    } else {
                        projectionOperation.set(projectionOperation.get().and(k).as(k));
                    }
                    if (DataFormatEnum.DATETIME.name().equals(format)) {
                        //如果是时间类的，就转换成配置中设置的格式

                        String dateToStringArg = DateOperators.dateOf(k).toString("%Y-%m-%d").toDocument().toJson();
                        if (DataProcessDimensionSourceEnum.CALENDAR_YEAR.name().equals(rule)) {
                            operations.add(projectionOperation.get().andExpression(dateToStringArg).substring(0, 4).as(rule));
                        } else if (DataProcessDimensionSourceEnum.CALENDAR_MONTH.name().equals(rule)) {
                            operations.add(projectionOperation.get().andExpression(dateToStringArg).substring(0, 7).as(rule));
                        } else if (DataProcessDimensionSourceEnum.CALENDAR_DAY.name().equals(rule)) {
                            operations.add(projectionOperation.get().andExpression(dateToStringArg).substring(0, 10).as(rule));
                        } else {
                            operations.add(projectionOperation.get().andExpression(DateOperators.dateOf(k).toString("%Y-%m-%d").toString()).as(rule));
                        }
                        groupReference.get().put("field", rule);
                        groupReference.get().put("name", rule);
                        operations.add(Aggregation.match(Criteria.where(rule).is(v)));
                        return;
                    } else {
                        operations.add(Aggregation.match(Criteria.where(k).is(v)));
                    }
                    //查找查询条件的下级字段，如果存在就用下级字段进行分组统计，通过下级字段分组统计的结果可以用于占比、排名的统计
                    //没有下级字段的就只能进行总量统计，不再进行后续的占比和排名统计，判断条件就是根据groupResult的输出的结果中的的
                    // 分组结果数量,超过1个就要进行占比和排名分析。
                    JSONObject childJSONObject = dataFieldChildMap.getJSONObject(k);
                    if (childJSONObject == null) {
                        //在提取的每个字段的子级查询条件无法查询到，说明本字段就是在本分组中属于最末一级，直接按本字段分组
                        //不存在子级，按本级进行分组
                        groupReference.get().put("field", k);
                        groupReference.get().put("name", columnName.getString("name"));
                        return;
                    }
                    String child = childJSONObject.getString("child");
                    //String group = childJSONObject.getString("group");
                    if (child != null) {
                        //存在下级可查询条件，按下级进行分组，同时还要判断子级字段不在本次查询参数中
                        if (queryValueMap.getString(child) != null) {
                            //说明子级在查询参数中，不需要针对本字段查询条件进行分组设置
                            return;
                        }
                        columnName = kv.getJSONObject(child).getJSONObject("columnName");
                        groupReference.get().put("field", child);
                        groupReference.get().put("name", columnName.getString("name"));
                    } else {
                        //不存在子级，按本级进行分组
                        groupReference.get().put("field", k);
                        groupReference.get().put("name", columnName.getString("name"));
                    }
                });

            }else {
                //本组查询条件没有输入任何值，表现为前端在本分组的筛选条件选择的是“全部”，需要根据本分组的首列进行分组聚合查询
                String k = ((JSONObject) item).getString("root");
                JSONObject columnName = kv.getJSONObject(k).getJSONObject("columnName");
                //TODO 新版本的format放到columnName字段的rule中了
                JSONObject formatJSONObject = kv.getJSONObject(k).getJSONObject("format");
                String format = formatJSONObject.getString("value");
                String rule = formatJSONObject.getString("rule");
                //封装查询匹配条件
                if (projectionOperation.get() == null) {
                    projectionOperation.set(Aggregation.project(k));
                } else {
                    projectionOperation.set(projectionOperation.get().and(k).as(k));
                }
                if (DataFormatEnum.DATETIME.name().equals(format)) {
                    //如果是时间类的，就转换成配置中设置的格式

                    String dateToStringArg = DateOperators.dateOf(k).toString("%Y-%m-%d").toDocument().toJson();
                    if (DataProcessDimensionSourceEnum.CALENDAR_YEAR.name().equals(rule)) {
                        operations.add(projectionOperation.get().andExpression(dateToStringArg).substring(0, 4).as(rule));
                    } else if (DataProcessDimensionSourceEnum.CALENDAR_MONTH.name().equals(rule)) {
                        operations.add(projectionOperation.get().andExpression(dateToStringArg).substring(0, 7).as(rule));
                    } else if (DataProcessDimensionSourceEnum.CALENDAR_DAY.name().equals(rule)) {
                        operations.add(projectionOperation.get().andExpression(dateToStringArg).substring(0, 10).as(rule));
                    } else {
                        operations.add(projectionOperation.get().andExpression(DateOperators.dateOf(k).toString("%Y-%m-%d").toString()).as(rule));
                    }
                    groupReference.get().put("field", rule);
                    groupReference.get().put("name", rule);
                }else {
                    //查找查询条件的下级字段，如果存在就用下级字段进行分组统计，通过下级字段分组统计的结果可以用于占比、排名的统计
                    //没有下级字段的就只能进行总量统计，不再进行后续的占比和排名统计，判断条件就是根据groupResult的输出的结果中的的
                    // 分组结果数量,超过1个就要进行占比和排名分析。
                    groupReference.get().put("field", k);
                    groupReference.get().put("name", columnName.getString("name"));
                }
            }

            String groupField = groupReference.get().getString("field");
            String groupName = groupReference.get().getString("name");

            groupOperation.set(Aggregation.group(groupField).first(groupField).as(groupName));

            aggregateFieldList.forEach(e -> {
                JSONObject aggregateColumnName = kv.getJSONObject(e).getJSONObject("columnName");
                groupOperation.set(groupOperation.get()
                        .sum(e).as(aggregateColumnName.getString("name"))
                        .count().as("订单数")
                        .avg(e).as("均单值"));
            });

            operations.add(groupOperation.get());
            Aggregation agg = Aggregation.newAggregation(operations);
            AggregationResults<JSONObject> result = mongoTemplate.aggregate(agg, source, JSONObject.class);
            //将查询统计的结果按照指定的顺序放到结果集中
            groupResult.put(queryMap.getJSONObject(g).getString("value"), result.getMappedResults());

        });

        return groupResult;
    }

    /**
     *  个分组维度需要联合交叉查询和分析，在组织匹配条件的时候时候所有输入条件一起参与匹配，需要变换组合的是group的
     *  的条件。组合的方法是将各个分组的最后一级（如果存在）作为group的reference field来进行统计分析
     * @param queryDTO ：查询参数格式是json，参数组装规则：
     *                {
     *                      条件分组名称1：{查询条件字段名称1：查询条件值，……  ，查询条件字段名称n：查询条件值}，
     *                               ……
     *                       条件分组名称n：{查询条件字段名称1：查询条件值，……  ，查询条件字段名称n：查询条件值}
     *                }
     * @return
     */
    public JSONObject unionAnalyze(QueryDTO queryDTO) {
        JSONObject dataStructure =  findDataStructureTemplateById(queryDTO.getQueryId());
        String dataQueryForestStr = mongoTemplate.findOne(new Query(Criteria.where("id").is(queryDTO.getQueryId())), String.class, "user_data_tree");

        JSONObject kv = dataStructure.getJSONObject("kv");
        JSONObject aggregate = kv.getJSONObject(DataProcessEnum.AGGREGATE.name());
        String source = kv.getJSONObject("source").getString("value");

        JSONObject extra = JSONObject.parseObject(dataQueryForestStr).getJSONObject("extra");
        JSONObject queryMap = extra.getJSONObject("queryMap");

        JSONObject dataFieldChildMap = extra.getJSONObject("dataFieldChild");

        //封装查询条件，查询条件根据筛选分组进行独立查询，比如有组织类、分类属性这个两个大的查询分组条件，
        //那么就需要针对两组条件进行隔离查询，也就是在进行组织类筛选的时候不匹配分类属性，反之亦然。
        //如果分组参数为空，就需要根据每个分组的首列进行分组统计,为空就是等于“全部”
        JSONObject queryGroupMap = queryDTO.getQueryMap();

        JSONObject groupResult = new JSONObject();
        List<String> aggregateFieldList = new ArrayList<>();
        aggregate.forEach((k, v) -> {
            aggregateFieldList.add(k);
        });

        //首先把所有可以参与group的field找出来，同时也找出用于计算占比用的总量sum的上一级group的field找出来
        AtomicReference<Map<String,JSONObject>> groupReference = new AtomicReference<>();
        AtomicReference<Map<String,JSONObject>> groupSumReference = new AtomicReference<>();

        groupReference.set(new HashMap<>());
        groupSumReference.set(new HashMap<>());
        //List<AggregationOperation> operations = new ArrayList<>();
        AtomicReference<Criteria> aggregationMachOperation = new AtomicReference<>();
        aggregationMachOperation.set(Criteria.where("templateId").is(queryDTO.getQueryId()));
        queryMap.forEach((g, item)->{
            JSONObject queryValueMap = queryGroupMap.getJSONObject(g);
            if(queryValueMap != null) {
                //查询条件有值
                queryValueMap.forEach((k, v) -> {
                    JSONObject groupReferenceInfo = new JSONObject();
                    JSONObject columnName = kv.getJSONObject(k).getJSONObject("columnName");
                    //TODO 新版本的format放到columnName字段的rule中了
                    JSONObject formatJSONObject = kv.getJSONObject(k).getJSONObject("format");
                    String format = formatJSONObject.getString("value");
                    String rule = formatJSONObject.getString("rule");
                    //对应字段的个性化显示名称
                    String displayName = columnName.getString("name");
                    String asName = columnName.getString("value");
                    //时间字段单独处理，时间字段我们认为都是各种独立的分组
                    if (DataFormatEnum.DATETIME.name().equals(format)) {
                        if (DataProcessDimensionSourceEnum.CALENDAR_YEAR.name().equals(rule)) {

                        } else if (DataProcessDimensionSourceEnum.CALENDAR_MONTH.name().equals(rule)) {

                        } else if (DataProcessDimensionSourceEnum.CALENDAR_DAY.name().equals(rule)) {

                        } else {

                        }
                        groupReferenceInfo.put("field", asName);
                        groupReferenceInfo.put("name", displayName);
                    }else {
                        //查找查询条件的下级字段，如果存在就用下级字段进行分组统计，通过下级字段分组统计的结果可以用于占比、排名的统计
                        //没有下级字段的就只能进行总量统计，不再进行后续的占比和排名统计，判断条件就是根据groupResult的输出的结果中的的
                        // 分组结果数量,超过1个就要进行占比和排名分析。
                        JSONObject childJSONObject = dataFieldChildMap.getJSONObject(k);
                        if (childJSONObject == null) {
                            //在提取的每个字段的子级查询条件无法查询到，说明本字段就是在本分组中属于最末一级，直接按本字段分组
                            //不存在子级，按本级进行分组
                            groupReferenceInfo.put("field", k);
                            groupReferenceInfo.put("name", asName);
                        } else {
                            String child = childJSONObject.getString("child");
                            //String group = childJSONObject.getString("group");
                            if (child != null) {
                                //存在下级可查询条件，按下级进行分组，同时还要判断子级字段不在本次查询参数中
                                if (queryValueMap.getString(child) != null) {
                                    //说明子级在查询参数中，不需要针对本字段查询条件进行分组设置
                                    return;
                                }
                                columnName = kv.getJSONObject(child).getJSONObject("columnName");
                                displayName = columnName.getString("name");
                                asName = columnName.getString("value");
                                groupReferenceInfo.put("field", child);
                                groupReferenceInfo.put("name", displayName);
                            } else {
                                //不存在子级，按本级进行分组
                                groupReferenceInfo.put("field", k);
                                groupReferenceInfo.put("name", displayName);
                            }
                        }
                    }
                    groupReference.get().put(asName, groupReferenceInfo);
                    groupSumReference.get().put(asName, groupReferenceInfo);
                });

            }else {
                //本组查询条件没有输入任何值，表现为前端在本分组的筛选条件选择的是“全部”，需要根据本分组的首列进行分组聚合查询
                String k = ((JSONObject) item).getString("root");
                JSONObject columnName = kv.getJSONObject(k).getJSONObject("columnName");
                //TODO 新版本的format放到columnName字段的rule中了
                JSONObject formatJSONObject = kv.getJSONObject(k).getJSONObject("format");
                String format = formatJSONObject.getString("value");
                String rule = formatJSONObject.getString("rule");
                String displayName = columnName.getString("name");
                String asName = columnName.getString("value");
                JSONObject groupReferenceInfo = new JSONObject();
                if (DataFormatEnum.DATETIME.name().equals(format)) {
                    //如果是时间类的，就转换成配置中设置的格式
                    groupReferenceInfo.put("field", asName);
                    groupReferenceInfo.put("name", displayName);
                }else {
                    //查找查询条件的下级字段，如果存在就用下级字段进行分组统计，通过下级字段分组统计的结果可以用于占比、排名的统计
                    //没有下级字段的就只能进行总量统计，不再进行后续的占比和排名统计，判断条件就是根据groupResult的输出的结果中的的
                    // 分组结果数量,超过1个就要进行占比和排名分析。
                    groupReferenceInfo.put("field", k);
                    groupReferenceInfo.put("name", displayName);
                }
                groupReference.get().put(asName, groupReferenceInfo);
                groupSumReference.get().put(asName, groupReferenceInfo);
            }
        });

        //把所有输入查询条件组装成匹配条件
        AtomicReference<ProjectionOperation> projectionOperation = new AtomicReference<>();
        projectionOperation.set(Aggregation.project());
//        aggregateFieldList.forEach(e -> {
//            if (projectionOperation.get() == null) {
//                projectionOperation.set(Aggregation.project());
//            }
//            projectionOperation.set(projectionOperation.get().and(e).as(e));
//        });

        queryGroupMap.forEach((g, kvMap)->{
            JSONObject queryValueMap = (JSONObject) kvMap;
            //operations.add(Aggregation.match(Criteria.where("templateId").is(queryBO.getQueryId())));

            queryValueMap.forEach((k, v) -> {
//                JSONObject columnName = kv.getJSONObject(k).getJSONObject("columnName");
                //TODO 新版本的format放到columnName字段的rule中了
                JSONObject formatJSONObject = kv.getJSONObject(k).getJSONObject("format");
                String format = formatJSONObject.getString("value");
                String rule = formatJSONObject.getString("rule");
//                String displayName = columnName.getString("name");
//                String asName = columnName.getString("value");
//                if (projectionOperation.get() == null) {
//                    projectionOperation.set(Aggregation.project());
//                }
//                projectionOperation.set(projectionOperation.get().and(k).as(k));

                //封装查询匹配条件
                if (DataFormatEnum.DATETIME.name().equals(format)) {
                    //如果是时间类的，就转换成配置中设置的格式
                    String start;
                    String end;
                    //String dateToStringArg = DateOperators.dateOf(k).toString("%Y-%m-%d").toDocument().toJson();
                    if (DataProcessDimensionSourceEnum.CALENDAR_YEAR.name().equals(rule)) {
                        start = DateUtils.getDateToString(DateUtils.getYearFirstDate(DateUtils.getDate((String)v)));
                        end = DateUtils.getDateToString(DateUtils.getYearLastDate(DateUtils.getDate((String)v)));
                        //projectionOperation.set(projectionOperation.get().andExpression(dateToStringArg).substring(0, 4).as(displayName));
                    } else if (DataProcessDimensionSourceEnum.CALENDAR_MONTH.name().equals(rule)) {
                        start = DateUtils.getDateToString(DateUtils.getMonthFirstDate(DateUtils.getDate((String)v)));
                        end = DateUtils.getDateToString(DateUtils.getMonthLastDate(DateUtils.getDate((String)v)));
                        //projectionOperation.set(projectionOperation.get().andExpression(dateToStringArg).substring(0, 7).as(displayName));
                    } else if (DataProcessDimensionSourceEnum.CALENDAR_DAY.name().equals(rule)) {
                        start = DateUtils.getDateToString(DateUtils.getDayFirstDate(DateUtils.getDate((String)v)));
                        end = DateUtils.getDateToString(DateUtils.getDayLastDate(DateUtils.getDate((String)v)));
                        //projectionOperation.set(projectionOperation.get().andExpression(dateToStringArg).substring(0, 10).as(displayName));
                    } else {
                        start = DateUtils.getCurrentDate();
                        end = start;
                    }
                    Date isoStart = DateUtils.dateToISODate(start);
                    Date isoEnd = DateUtils.dateToISODate(end);
                    aggregationMachOperation.set(aggregationMachOperation.get().and(k).lt(isoEnd).gte(isoStart));
                } else {
                    aggregationMachOperation.set(aggregationMachOperation.get().and(k).is(v));
                }
            });
        });

        //把匹配条件与每个group reference field一起进行查询分析

        groupReference.get().forEach((k, referenceFieldInfo)->{
            List<AggregationOperation> aggregationOperations = new ArrayList<>();
            String groupField = referenceFieldInfo.getString("field");
            String groupName = referenceFieldInfo.getString("name");
            AtomicReference<GroupOperation> groupOperation = new AtomicReference<>();
            groupOperation.set(Aggregation.group(groupField).first(groupField).as(groupName));
            //aggregationOperations.add(projectionOperation.get());
            aggregationOperations.add(Aggregation.match(aggregationMachOperation.get()));
            aggregateFieldList.forEach(e -> {
                JSONObject aggregateColumnName = kv.getJSONObject(e).getJSONObject("columnName");
                groupOperation.set(groupOperation.get()
                        .sum(e).as(SUM.name() + "-" + aggregateColumnName.getString("name"))
                        .count().as(COUNT.name() + "-订单数")
                        .avg(e).as(AVG.name() + "-均单值"));
            });
            aggregationOperations.add(groupOperation.get());
            aggregationOperations.add(Aggregation.sort(Sort.by(new Sort.Order(Sort.Direction.DESC, SUM.name() + "-" + "订单额"))));
            Aggregation agg = Aggregation.newAggregation(aggregationOperations);
            AggregationResults<JSONObject> result = mongoTemplate.aggregate(agg, source, JSONObject.class);
            //将查询统计的结果按照指定的顺序放到结果集中
            groupResult.put(groupName, ratioGroupResults(result.getMappedResults()));
        });

        return groupResult;
    }

    /**
     *  个分组维度需要联合交叉查询和分析，在组织匹配条件的时候时候所有输入条件一起参与匹配，需要变换组合的是group的
     *  的条件。组合的方法是将各个分组的最后一级（如果存在）作为group的reference field来进行统计分析
     * @param processDTO ：查询参数格式是json，参数组装规则：
     *                {
     *                      条件分组名称1：{查询条件字段名称1：查询条件值，……  ，查询条件字段名称n：查询条件值}，
     *                               ……
     *                       条件分组名称n：{查询条件字段名称1：查询条件值，……  ，查询条件字段名称n：查询条件值}
     *                }
     * @return
     */
    public List<JSONObject> analyze(ProcessDTO processDTO) {
        JSONObject dataStructure =  mongoTemplate.findOne(new Query(Criteria.where("id").is(processDTO.getQueryId())), JSONObject.class, "user_template");
        JSONObject kv = dataStructure.getJSONObject("kv");
        String source = kv.getJSONObject("source").getString("value");

        //封装查询条件，查询条件根据筛选分组进行独立查询，比如有组织类、分类属性这个两个大的查询分组条件，
        //那么就需要针对两组条件进行隔离查询，也就是在进行组织类筛选的时候不匹配分类属性，反之亦然。
        //如果分组参数为空，就需要根据每个分组的首列进行分组统计,为空就是等于“全部”
        Map<String, String> queryMap = processDTO.getQueryMap();
        final Criteria[] aggregationMachOperation = {null};
        //把所有输入查询条件组装成匹配条件
        if(queryMap != null) {
            queryMap.forEach((k, v) -> {
                if (aggregationMachOperation[0] == null) {
                    aggregationMachOperation[0] = Criteria.where(k).is(v);
                } else {
                    //封装查询匹配条件
                    aggregationMachOperation[0] = aggregationMachOperation[0].and(k).is(v);
                }
            });
        }

//        MatchOperation match = Aggregation.match(new Criteria("workflow_stage_current_assignee").ne(null)
//                .andOperator(new Criteria("CreatedDate").gte(new Date(fromDate.getTimeInMillis()))
//                        .andOperator(new Criteria("CreatedDate").lte(new Date(toDate.getTimeInMillis())))));

//        GroupOperation group = Aggregation.group(processDTO.getGroupList().toArray(new String[0]));
//
//        ProjectionOperation project = Aggregation.project(processDTO.getGroupList().toArray(new String[0]));

        //SortOperation sort=Aggregation.sort(Sort.Direction.DESC,"exceptions");

        //Aggregation aggregation  = Aggregation.newAggregation(match,group,project);

        //AggregationResults<ExceptionCount> output  = mongoTemplate.aggregate(aggregation, "EXCEPTIONS", ExceptionCount.class);


        //把匹配条件与每个group reference field一起进行查询分析
        AtomicReference<GroupOperation> groupOperation = new AtomicReference<>();
        //将分组字段输出
        ProjectionOperation project = Aggregation.project(processDTO.getProjectList().toArray(new String[0]));
        groupOperation.set(Aggregation.group(processDTO.getGroupList().toArray(new String[0])));
        List<AggregationOperation> aggregationOperations = new ArrayList<>();
        if(queryMap != null) {
            aggregationOperations.add(Aggregation.match(aggregationMachOperation[0]));
        }

        Map<String, Map<String, String>> aggregateMap = processDTO.getAggregateMap();
        Map<String, String> SUM = aggregateMap.get("SUM");
        if(SUM != null) {
            SUM.forEach((fieldName, asName) -> {
                groupOperation.set(groupOperation.get()
                        .sum(fieldName).as(asName));
            });
        }
        Map<String, String> AVG = aggregateMap.get("AVG");
        if(AVG != null) {
            AVG.forEach((fieldName, asName) -> {
                groupOperation.set(groupOperation.get()
                        .avg(fieldName).as(asName));
            });
        }
        Map<String, String> MAX = aggregateMap.get("MAX");
        if(MAX != null) {
            MAX.forEach((fieldName, asName) -> {
                groupOperation.set(groupOperation.get()
                        .max(fieldName).as(asName));
            });
        }
        Map<String, String> MIN = aggregateMap.get("MIN");
        if(MIN != null) {
            MIN.forEach((fieldName, asName) -> {
                groupOperation.set(groupOperation.get()
                        .min(fieldName).as(asName));
            });
        }
        groupOperation.set(groupOperation.get().count().as(COUNT.name()));

        aggregationOperations.add(groupOperation.get());
        aggregationOperations.add(project);

        //aggregationOperations.add(Aggregation.sort(Sort.by(new Sort.Order(Sort.Direction.DESC, SUM.name() + "-" + "订单额"))));
        Aggregation agg = Aggregation.newAggregation(aggregationOperations);
        AggregationResults<JSONObject> result = mongoTemplate.aggregate(agg, source, JSONObject.class);
        //将查询统计的结果按照指定的顺序放到结果集中
        return result.getMappedResults();
    }
    /**
     * 对group聚合后的数据进行累计求和
     */
    public JSONObject ratioGroupResults(List<JSONObject> groupResults) {
        JSONObject result = new JSONObject();
        JSONObject gather = new JSONObject();
        JSONObject sort = new JSONObject();
        JSONObject ratio = new JSONObject();
        Map<String, TreeMap<String, BigDecimal>> sortMap = new HashMap<>();
        result.put("gather", gather);
        result.put("sort", groupResults);
        result.put("ratio", ratio);
        groupResults.forEach(e->{
            e.forEach((k,v)->{
                String[] keys = k.split("-");
                if(keys.length > 1) {
                    JSONObject gatherItem = gather.getJSONObject(keys[0]);
                    if(gatherItem == null) {
                        gatherItem = new JSONObject();
                        gather.put(keys[0], gatherItem);
                        if(v instanceof  Integer) {
                            gatherItem.put(keys[1], new BigDecimal((Integer) v));
                        }else {
                            gatherItem.put(keys[1], new BigDecimal((Double) v));
                        }

                    }else {
                        BigDecimal value = gatherItem.getBigDecimal(keys[1]);
                        if(value == null) {
                            if (!DataProcessEnum.AVG.name().equals(keys[0])) {
                                if(v instanceof  Integer) {
                                    gatherItem.put(keys[1], new BigDecimal((Integer) v));
                                }else {
                                    gatherItem.put(keys[1], new BigDecimal((Double) v));
                                }
                            }
                        }else {
                            if (!DataProcessEnum.AVG.name().equals(keys[0])) {
                                if (v instanceof Integer) {
                                    gatherItem.put(keys[1], new BigDecimal((Integer) v).add(value));
                                } else {
                                    gatherItem.put(keys[1], new BigDecimal((Double) v).add(value));
                                }
                            }
                        }
                    }

                    TreeMap sortTreeMap = sortMap.get(keys[0]);
                    if(sortTreeMap == null) {

                    }
                }
            });
        });

        //占比计算，分组成员少于2个的不做占比计算
        if(groupResults.size() > 1) {
            groupResults.forEach(e->{
                String groupValue = e.getString("_id");
                e.forEach((k, v)->{
                    String[] keys = k.split("-");
                    if(keys.length > 1 && !DataProcessEnum.AVG.name().equals(keys[0])) {
                        BigDecimal total = gather.getJSONObject(keys[0]).getBigDecimal(keys[1]);
                        JSONObject gatherRatio = ratio.getJSONObject(groupValue);
                        if (gatherRatio == null) {
                            gatherRatio = new JSONObject();
                            ratio.put(groupValue, gatherRatio);
                            if (v instanceof Integer) {
                                gatherRatio.put(keys[1], new BigDecimal((Integer) v).divide(total,2, RoundingMode.HALF_UP));
                            } else {
                                gatherRatio.put(keys[1], new BigDecimal((Double) v).divide(total,2, RoundingMode.HALF_UP));
                            }
                        } else {
                            BigDecimal value = gatherRatio.getBigDecimal(keys[1]);
                            if (value == null) {
                                if (v instanceof Integer) {
                                    gatherRatio.put(keys[1], new BigDecimal((Integer) v).divide(total,2, RoundingMode.HALF_UP));
                                } else {
                                    gatherRatio.put(keys[1], new BigDecimal((Double) v).divide(total,2, RoundingMode.HALF_UP));
                                }
                            } else {
                                if (v instanceof Integer) {
                                    gatherRatio.put(keys[1], new BigDecimal((Integer) v).add(value).divide(total,2, RoundingMode.HALF_UP));
                                } else {
                                    gatherRatio.put(keys[1], new BigDecimal((Double) v).add(value).divide(total,2, RoundingMode.HALF_UP));
                                }
                            }
                        }
                    }
                });
            });
        }
        return result;
    }

    /**
     * 暂时根据公司的业务特点，趋势只以时间序列的月份为单位进行分组统计即可，不进行多维度趋势分析。
     * @param queryDTO
     * @return
     */
    public JSONObject timeSeriesTendency(QueryDTO queryDTO) {
        JSONObject dataStructure =  findDataStructureTemplateById(queryDTO.getQueryId());
        String dataQueryForestStr = mongoTemplate.findOne(new Query(Criteria.where("id").is(queryDTO.getQueryId())), String.class, "user_data_tree");

        JSONObject kv = dataStructure.getJSONObject("kv");
        JSONObject aggregate = kv.getJSONObject(DataProcessEnum.AGGREGATE.name());
        String source = kv.getJSONObject("source").getString("value");

        JSONObject extra = JSONObject.parseObject(dataQueryForestStr).getJSONObject("extra");
        JSONObject queryMap = extra.getJSONObject("queryMap");

        JSONObject dataFieldChildMap = extra.getJSONObject("dataFieldChild");

        //封装查询条件，查询条件根据筛选分组进行独立查询，比如有组织类、分类属性这个两个大的查询分组条件，
        //那么就需要针对两组条件进行隔离查询，也就是在进行组织类筛选的时候不匹配分类属性，反之亦然。
        //如果分组参数为空，就需要根据每个分组的首列进行分组统计,为空就是等于“全部”
        JSONObject queryGroupMap = queryDTO.getQueryMap();
        //进行分组统计
        JSONObject groupResult = new JSONObject();

        queryMap.forEach((g, item)->{
            JSONObject queryValueMap = queryGroupMap.getJSONObject(g);

            List<AggregationOperation> operations = new ArrayList<>();

            List<String> aggregateFieldList = new ArrayList<>();
            aggregate.forEach((k, v) -> {
                aggregateFieldList.add(k);
            });

            AtomicReference<GroupOperation> groupOperation = new AtomicReference<>();
            AtomicReference<JSONObject> groupReference = new AtomicReference<>();
            groupReference.set(new JSONObject());
            operations.add(Aggregation.match(Criteria.where("templateId").is(queryDTO.getQueryId())));

            AtomicReference<ProjectionOperation> projectionOperation = new AtomicReference<>();
            //operations.add(Aggregation.sort(Sort.by(new Sort.Order(Sort.Direction.DESC, "10"))));
            aggregateFieldList.forEach(e -> {
                if (projectionOperation.get() == null) {
                    projectionOperation.set(Aggregation.project(e));
                } else {
                    projectionOperation.set(projectionOperation.get().and(e).as(e));
                }
            });


            //本组查询条件没有输入任何值，表现为前端在本分组的筛选条件选择的是“全部”，需要根据本分组的首列进行分组聚合查询
            String k = ((JSONObject) item).getString("root");
            JSONObject columnName = kv.getJSONObject(k).getJSONObject("columnName");
            //TODO 新版本的format放到columnName字段的rule中了
            JSONObject formatJSONObject = kv.getJSONObject(k).getJSONObject("format");
            String format = formatJSONObject.getString("value");
            String rule = formatJSONObject.getString("rule");
            //对应字段的个性化显示名称
            String displayName = columnName.getString("name");
            String asName = columnName.getString("value");
            //封装查询匹配条件
            if (DataFormatEnum.DATETIME.name().equals(format)) {
                //如果是时间类的，就转换成配置中设置的格式
                if (projectionOperation.get() == null) {
                    projectionOperation.set(Aggregation.project(k));
                } else {
                    projectionOperation.set(projectionOperation.get().and(k).as(k));
                }
                String dateToStringArg = DateOperators.dateOf(k).toString("%Y-%m-%d").toDocument().toJson();
                if (DataProcessDimensionSourceEnum.CALENDAR_YEAR.name().equals(rule)) {
                    operations.add(projectionOperation.get().andExpression(dateToStringArg).substring(0, 4).as(asName));
                } else if (DataProcessDimensionSourceEnum.CALENDAR_MONTH.name().equals(rule)) {
                    operations.add(projectionOperation.get().andExpression(dateToStringArg).substring(0, 7).as(asName));
                } else if (DataProcessDimensionSourceEnum.CALENDAR_DAY.name().equals(rule)) {
                    operations.add(projectionOperation.get().andExpression(dateToStringArg).substring(0, 10).as(asName));
                } else {
                    operations.add(projectionOperation.get().andExpression(DateOperators.dateOf(k).toString("%Y-%m-%d").toString()).as(rule));
                }
                groupReference.get().put("field", asName);
                groupReference.get().put("name", displayName);

                groupOperation.set(Aggregation.group(asName).first(asName).as(displayName));

                aggregateFieldList.forEach(e -> {
                    JSONObject aggregateColumnName = kv.getJSONObject(e).getJSONObject("columnName");
                    groupOperation.set(groupOperation.get()
                            .sum(e).as(aggregateColumnName.getString("name"))
                            .count().as("订单数")
                            .avg(e).as("均单值"));
                });

                operations.add(groupOperation.get());
                Aggregation agg = Aggregation.newAggregation(operations);
                AggregationResults<JSONObject> result = mongoTemplate.aggregate(agg, source, JSONObject.class);
                //将查询统计的结果按照指定的顺序放到结果集中
                groupResult.put(queryMap.getJSONObject(g).getString("value"), result.getMappedResults());
            }
        });
        return groupResult;
    }
    /**
     * project:列出所有本次查询的字段，包括查询条件的字段和需要搜索的字段；
     * match:搜索条件criteria
     * unwind：某一个字段是集合，将该字段分解成数组
     * group：分组的字段，以及聚合相关查询
     *      sum：求和(同sql查询)
     *      count：数量(同sql查询)
     *      as:别名(同sql查询)
     *      addToSet：将符合的字段值添加到一个集合或数组中
     * sort：排序
     * skip&limit：分页查询
     */
    public List<JSONObject> customerDetailList(Integer pageNum,String userId,String buyerNick,String itemId,List<String> phones) throws Exception{
        Criteria criteria = Criteria.where("userId").is(userId);
        Long pageSize = 10L;
        Long startRows = (pageNum - 1) * pageSize;
        if(buyerNick != null && !"".equals(buyerNick)){
            criteria.and("buyerNick").is(buyerNick);
        }
        if(phones != null && phones.size() > 0){
            criteria.and("mobile").in(phones);
        }
        if(itemId != null && !"".equals(itemId)){
            criteria.and("orders.numIid").is(itemId);
        }
        Aggregation customerAgg = Aggregation.newAggregation(
                Aggregation.project("buyerNick","payment","num","tid","userId","address","mobile","orders"),
                Aggregation.match(criteria),Aggregation.unwind("orders"),
                Aggregation.group("buyerNick").first("buyerNick").as("buyerNick").first("mobile").as("mobile").
                        first("address").as("address").sum("payment").as("totalPayment").sum("num").as("itemNum").count().as("orderNum"),
                Aggregation.sort(Sort.by(new Sort.Order(Sort.Direction.DESC, "totalPayment"))),
                Aggregation.skip(startRows),
                Aggregation.limit(pageSize)
        );
        List<JSONObject> customerList = findAggregateList(new Query(criteria), userId, customerAgg,JSONObject.class);
        return customerList;
    }

    public <T> List<T> findAggregateList(Query query,String userNickName, Aggregation aggregation,Class<T> clazz) {
        AggregationResults<T> aggregate = this.mongoTemplate.aggregate(aggregation, "collectionName", clazz);
        List<T> customerDetails = aggregate.getMappedResults();
        return customerDetails;
    }

    //浏览分组
    private List<JSONObject> flowGroup() {
        Criteria criteria = Criteria.where("type").is("Constants.DataSetOpt.OPT_VIEW");
        Aggregation agg = Aggregation.newAggregation(
                Aggregation.match(criteria),
                Aggregation.group("dataSetId")
                        .first("dataSetId").as("dataSetId")
                        .count()
                        .as("dataSetIdCount")
                        .first("dataSetType").as("dataSetType")

        );
        AggregationResults<JSONObject> result = mongoTemplate.aggregate(agg,JSONObject.class ,JSONObject.class);
        return result.getMappedResults();
    }

    //@Test
    public void statTest(){
        TypedAggregation<JSONObject> agg = Aggregation.newAggregation(JSONObject.class,
                Aggregation.group("1")
                        .sum("10")
                        .as("10")
                        .sum("9")
                        .as("9"));
        AggregationResults<JSONObject> result = mongoTemplate.aggregate(agg, "user_sell", JSONObject.class);

//        return result.getMappedResults();
        result.getMappedResults().forEach(document -> System.out.println(document));
    }

    public double getTotleScoreWithMongoTemplate(JSONObject studentScore) {

        //封装查询条件
        List<AggregationOperation> operations = new ArrayList<>();

        if (StringUtils.isEmpty(studentScore.getString("name")) && StringUtils.isEmpty(studentScore.getString("course"))){

            //totleScore为StudentScore类中新建的属性，用于接收统计后的总分数；当然也可以使用score（或其他属性）接收
            operations.add(Aggregation.group("id").sum("score").as("totleScore"));
        }
        if (!StringUtils.isEmpty(studentScore.getString("name"))) {
            operations.add(Aggregation.match(Criteria.where("name").is(studentScore.getString("name"))));
            operations.add(Aggregation.group("name").sum("score").as("totleScore"));
        }
        if (!StringUtils.isEmpty(studentScore.getString("course"))) {
            operations.add(Aggregation.match(Criteria.where("course").is(studentScore.getString("name"))));
            operations.add(Aggregation.group("course").sum("score").as("totleScore"));
        }
        Aggregation aggregation = Aggregation.newAggregation(operations);

        //查询、并获取结果
        AggregationResults<JSONObject> results = mongoTemplate.aggregate(aggregation, "studentScore", JSONObject.class);
        double totleScore = results.getUniqueMappedResult().getDouble("totleScore");

        return totleScore;
    }

    private List<JSONObject> groupCountryOfOrigin() {
        Aggregation agg = Aggregation.newAggregation(
                Aggregation.group("countryOfOrigin")
                        .first("countryOfOrigin").as("countryOfOrigin")
                        .addToSet("portEntry").as("portEntryList")
                        .addToSet("province").as("provinceList")
                        .addToSet("customName").as("customNameList")
        );
        AggregationResults<JSONObject> result = mongoTemplate.aggregate(agg,JSONObject.class , JSONObject.class);
        return result.getMappedResults();
    }
}
