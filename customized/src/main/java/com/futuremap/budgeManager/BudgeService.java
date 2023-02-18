package com.futuremap.budgeManager;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.futuremap.base.dictionary.DictionaryFactory;
import com.futuremap.base.util.HttpClient;
import com.futuremap.datamanager.remoteDate.DataService;
import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Pullwind
 */
@Service
public class BudgeService extends DictionaryFactory {

    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private DataService dataService;

    public List<JSONObject> getList(BudgeQueryDTO budgeQueryDTO) {
        Map<String, JSONObject> globalDictionaryMap = new HashMap<>();
        List<JSONObject> globalDictionaryList = mongoTemplate.findAll(JSONObject.class, "global_dictionary");
        globalDictionaryList.forEach(e->{
            globalDictionaryMap.put(e.getString("id"), e);
        });

        Map<String, JSONObject> budgeMap = new HashMap<>();

//        List<AggregationOperation> aggregationOperations = new ArrayList<>();
//
//        GroupOperation groupOperation = Aggregation.group("year").first("year").as("year");
//        aggregationOperations.add(Aggregation.match(Criteria.where("year").is(budgeQueryDTO.getYear())));
//        groupOperation = (groupOperation
//                .sum("{inlineChildrensum.0}").as("inlineChildren->sum->0"));
//        aggregationOperations.add(groupOperation);
//        Aggregation agg = Aggregation.newAggregation(aggregationOperations);
//        AggregationResults<JSONObject> result = mongoTemplate.aggregate(agg, "budge", JSONObject.class);
        List<JSONObject> list = mongoTemplate.find(new Query(Criteria.where("year").is(budgeQueryDTO.year).and("tradTypeId").is(budgeQueryDTO.getTradTypeId()).and("plateId").is(budgeQueryDTO.getPlateId())), JSONObject.class, "budge");
        if(list.size() == 0) {
            list = initBudge(budgeQueryDTO);
            mongoTemplate.insert(list, "budge");
            list = mongoTemplate.find(new Query(Criteria.where("year").is(budgeQueryDTO.year).and("tradTypeId").is(budgeQueryDTO.getTradTypeId()).and("plateId").is(budgeQueryDTO.getPlateId())), JSONObject.class, "budge");
        }
//        List<JSONObject> copyList = new ArrayList<>();
//        list.forEach(e->{
//            String plateId = e.getString("plateId");
//            String parentId = e.getString("parentId");
//            String tradTypeId = e.getString("tradTypeId");
//            JSONObject parentProduct = globalDictionaryMap.get(parentId);
//            JSONObject plate =  globalDictionaryMap.get(plateId);
//            JSONObject tradType =  globalDictionaryMap.get(tradTypeId);
//            JSONObject plateBudge = budgeMap.get(plateId);
//            if(plateBudge == null) {
//                plateBudge = new JSONObject();
//                plateBudge.put( "id", "sum");
//                plateBudge.put( "product", "合计");
//                plateBudge.put( "tradTypeId", tradTypeId);
//                plateBudge.put( "tradType", tradType.getString(tradTypeId));
//                plateBudge.put( "plateId", plateId);
//                plateBudge.put( "plate", plate.getString("name"));
//
//                JSONObject inlineFieldEdit = new JSONObject();
//                inlineFieldEdit.put("0", false);
//                inlineFieldEdit.put("1", false);
//                inlineFieldEdit.put("2", false);
//                plateBudge.put("inlineFieldEdit", inlineFieldEdit);
//
//                JSONObject inlineChildren = new JSONObject();
//                JSONObject field = new JSONObject();
//                field.put("0", "销售额");
//                field.put("1", "成本额");
//                field.put("2", "总销量");
//                inlineChildren.put("field", field);
//
//                JSONObject unit = new JSONObject();
//                unit.put("0", "元人民币");
//                unit.put("1", "元人民币");
//                unit.put("2", "吨（T）");
//                inlineChildren.put("unit", unit);
//                plateBudge.put("inlineChildren", inlineChildren);
//                budgeMap.put(plateId, plateBudge);
//                copyList.add(plateBudge);
//            }
//
//            JSONObject parentBudge = budgeMap.get(parentId);
//
//            if(parentBudge == null) {
//                parentBudge = new JSONObject();
//                parentBudge.put( "id", parentId);
//                parentBudge.put( "product", parentProduct.getString("name"));
//                parentBudge.put( "tradTypeId", tradTypeId);
//                parentBudge.put( "tradType", tradType.getString(tradTypeId));
//                parentBudge.put( "plateId", plateId);
//                parentBudge.put( "plate", plate.getString("name"));
//                parentBudge.put( "sequence", parentProduct.getString("value"));
//
//                JSONObject inlineFieldEdit = new JSONObject();
//                inlineFieldEdit.put("0", false);
//                inlineFieldEdit.put("1", false);
//                inlineFieldEdit.put("2", false);
//                parentBudge.put("inlineFieldEdit", inlineFieldEdit);
//
//                JSONObject inlineChildren = new JSONObject();
//                JSONObject field = new JSONObject();
//                field.put("0", "销售额");
//                field.put("1", "成本额");
//                field.put("2", "总销量");
//                inlineChildren.put("field", field);
//
//                JSONObject unit = new JSONObject();
//                unit.put("0", "元人民币");
//                unit.put("1", "元人民币");
//                unit.put("2", "吨（T）");
//                inlineChildren.put("unit", unit);
//                parentBudge.put("inlineChildren", inlineChildren);
//                budgeMap.put(parentId, parentBudge);
//                copyList.add(parentBudge);
//            }
//
//            JSONObject finalPlateBudge = plateBudge;
//            JSONObject finalParentBudge = parentBudge;
//            List<String> removeKeys = new ArrayList<>();
//            e.forEach((key, value)->{
//                String[] keys = UnionIdUtil.getIdsFromUnionId(key);
//                if(keys.length > 1) {
//                    for (int i = 0; i <keys.length; ) {
//                        String key1 = keys[i++];
//                        JSONObject plateObj0 = finalPlateBudge.getJSONObject(key1);
//                        JSONObject parentObj0 = finalParentBudge.getJSONObject(key1);
//
//                        String key2 = keys[i++];
//                        JSONObject plateObj1 = plateObj0.getJSONObject(key2);
//                        if(plateObj1 == null) {
//                            plateObj1 = new JSONObject();
//                            plateObj0.put(key2, plateObj1);
//                        }
//
//                        JSONObject parentObj1 = parentObj0.getJSONObject(key2);
//                        if(parentObj1 == null) {
//                            parentObj1 = new JSONObject();
//                            parentObj0.put(key2, parentObj1);
//                        }
//
//                        String key3 = keys[i++];
//                        BigDecimal plateBigDecimal = plateObj1.getBigDecimal(key3);
//                        if(plateBigDecimal == null) {
//                            plateBigDecimal = BigDecimal.ZERO;
//                        }
//                        plateObj1.put(key3, plateBigDecimal.add(new BigDecimal(e.getString(key))));
//
//                        BigDecimal parentBigDecimal = parentObj1.getBigDecimal(key3);
//                        if(parentBigDecimal == null) {
//                            parentBigDecimal = BigDecimal.ZERO;
//                        }
//                        parentObj1.put(key3, parentBigDecimal.add(new BigDecimal(e.getString(key))));
//                    }
//                    removeKeys.add(key);
//                }
//            });
//            removeKeys.forEach(key->{
//                e.remove(key);
//            });
//            copyList.add(e);
//        });
//        return copyList;
        return list;
    }

    public List<JSONObject> initBudge(BudgeQueryDTO budgeQueryDTO) {
        List<JSONObject> budgeTemplate = mongoTemplate.findAll(JSONObject.class, "budge_template");
        budgeTemplate.forEach(e->{
            e.put("year", budgeQueryDTO.getYear());
        });
        return budgeTemplate;
    }
//    {
//        "_id" : ObjectId("61b6e8d9ca89bed21a0f145a"),
//            "id" : "PD0015000",
//            "sequence" : "15",
//            "tradType" : "内销",
//            "tradTypeId" : "5000",
//            "plate" : "日化护理",
//            "plateId" : "P001",
//            "product" : "i+v",
//            "type" : "product",
//            "version" : "1.0.0",
//            "level" : 0,
//            "parentId" : "P001",
//            "parent" : "日化护理",
//            "IMPLEMENT_INIT" : {
//        "INLINE_CHILDREN" : {
//            "COUNT" : "TOTAL_MONTHS_OF_YEAR"
//        },
//        "yearMonth" : "YEAR_MONTH",
//                "month" : "MONTH",
//                "year" : "YEAR"
//    },
//        "edit" : true,
//            "amount" : "销售额",
//            "cost" : "成本额",
//            "quantity" : "销量",
//            "price" : "销售单价",
//            "perCost" : "单位成本",
//            "grossProfitRatio" : "毛利率",
//            "inlineFieldEdit" : {
//        "amount" : false,
//                "cost" : false,
//                "quantity" : true,
//                "price" : true,
//                "perCost" : true,
//                "grossProfitRatio" : false
//    }
//    }
    public List<JSONObject> initBudgeTemplate(BudgeQueryDTO budgeQueryDTO) {
        List<JSONObject> plateList = mongoTemplate.findAll(JSONObject.class, "product");
        List<JSONObject> productList = mongoTemplate.findAll(JSONObject.class, "product");
        //TODO 客户会提供对应表，再来初始化
        return null;
    }

    public void update(List<BudgeUpdateDTO> budgeUpdateDTOList) {
        budgeUpdateDTOList.forEach(e->{
            Update update = Update.update("inlineChildren", e.getInlineChildren());
            mongoTemplate.findAndModify(new Query(Criteria.where("id").is(e.getId())), update, JSONObject.class, "budge");
        });
    }

    public JSONObject getConfig() {
        String str = mongoTemplate.findOne(new Query(Criteria.where("id").is("61add7e6c2df3594491d2e20")), String.class, "user_data_tree");
        return JSONObject.parseObject(str);
    }
}

