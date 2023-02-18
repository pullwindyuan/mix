package com.futuremap.salseReport;

import com.alibaba.fastjson.JSONObject;
import com.futuremap.annotation.CustomResponse;
import com.futuremap.base.bean.Page;
import com.futuremap.base.dictionary.DictionaryFactory;
import com.futuremap.salseReport.salesDaily.SalesDailyDTO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Pullwind
 */
@Service
public class SalesReportService extends DictionaryFactory {

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<JSONObject> getRevenueStatisticsInfoList(SalesReportDTO salesReportDTO) {
        Map<String, JSONObject> globalDictionaryMap = new HashMap<>();
        List<JSONObject> globalDictionaryList = mongoTemplate.findAll(JSONObject.class, "global_dictionary");
        globalDictionaryList.forEach(e->{
            globalDictionaryMap.put(e.getString("id"), e);
        });
        return  mongoTemplate.find(new Query(Criteria.where("dateTime").is(salesReportDTO.getDateTime())), JSONObject.class, "sales_revenue_statistics");
    }

    public Page getDeliveryInfoList(SalesReportPageDTO salesReportPageDTO) {
        Map<String, JSONObject> globalDictionaryMap = new HashMap<>();
        List<JSONObject> globalDictionaryList = mongoTemplate.findAll(JSONObject.class, "global_dictionary");
        globalDictionaryList.forEach(e->{
            globalDictionaryMap.put(e.getString("id"), e);
        });
        return getPageData(salesReportPageDTO, "delivery_report");
    }

    public Page getOverdueReceivableList(SalesReportPageDTO salesReportPageDTO) {
        Map<String, JSONObject> globalDictionaryMap = new HashMap<>();
        List<JSONObject> globalDictionaryList = mongoTemplate.findAll(JSONObject.class, "global_dictionary");
        globalDictionaryList.forEach(e->{
            globalDictionaryMap.put(e.getString("id"), e);
        });
        return getPageData(salesReportPageDTO, "overdue_receivables");
    }

    public Page getDueReceivablesList(SalesReportPageDTO salesReportPageDTO) {
        Map<String, JSONObject> globalDictionaryMap = new HashMap<>();
        List<JSONObject> globalDictionaryList = mongoTemplate.findAll(JSONObject.class, "global_dictionary");
        globalDictionaryList.forEach(e->{
            globalDictionaryMap.put(e.getString("id"), e);
        });
        return getPageData(salesReportPageDTO, "due_receivables");
    }

    public Page getUnexecutedContractReportList(SalesReportPageDTO salesReportPageDTO) {
        Map<String, JSONObject> globalDictionaryMap = new HashMap<>();
        List<JSONObject> globalDictionaryList = mongoTemplate.findAll(JSONObject.class, "global_dictionary");
        globalDictionaryList.forEach(e->{
            globalDictionaryMap.put(e.getString("id"), e);
        });
        return getPageData(salesReportPageDTO, "unexecuted_contract_report");
    }

    public Page getNewContractReportList(SalesReportPageDTO salesReportPageDTO) {
        Map<String, JSONObject> globalDictionaryMap = new HashMap<>();
        List<JSONObject> globalDictionaryList = mongoTemplate.findAll(JSONObject.class, "global_dictionary");
        globalDictionaryList.forEach(e->{
            globalDictionaryMap.put(e.getString("id"), e);
        });
        return getPageData(salesReportPageDTO, "new_contract_report");
    }

    public JSONObject getBudgeTrackReport(@RequestBody SalesReportDTO salesReportDTO) {
        Map<String, JSONObject> globalDictionaryMap = new HashMap<>();
        List<JSONObject> globalDictionaryList = mongoTemplate.findAll(JSONObject.class, "global_dictionary");
        globalDictionaryList.forEach(e->{
            globalDictionaryMap.put(e.getString("id"), e);
        });
        return  mongoTemplate.findOne(new Query(Criteria.where("dateTime").is(salesReportDTO.getDateTime())), JSONObject.class, "budge_track");
    }

    private Page getPageData(SalesReportPageDTO salesReportPageDTO, String collection) {
        Criteria where = Criteria.where("dateTime").is(salesReportPageDTO.getDateTime());
        Query query=new Query(where);
        long count = mongoTemplate.count(query, collection);
        //Sort.Direction.DESC
        Pageable pageable = new PageRequest(salesReportPageDTO.getPage(), salesReportPageDTO.getSize(),
                //Sort.by(Sort.Direction.valueOf(salesReportPageDTO.getSortDirection()), salesReportPageDTO.getSortProperty())) {};
                Sort.by(Sort.Direction.DESC, "dateTime")) {};
        Page result = new Page();
        result.setCount(count);
        result.setPage( salesReportPageDTO.getPage());
        result.setSize(salesReportPageDTO.getSize());
        result.setList(mongoTemplate.find(query.with(pageable), JSONObject.class, collection));
        return result;
    }

    public List<JSONObject> demo() {
        LookupOperation lookupToLots = LookupOperation.newLookup().
                from("eCostRecord").//关联表名 lots
                localField("lsumpDocNumber").//关联字段
                foreignField("lsumpDocNumber").//主表关联字段对应的次表字段
                as("groups");//查询结果集合名

        //主表
        Criteria ordercri = Criteria.where("eccDocNo").ne(null).ne("");
        AggregationOperation match = Aggregation.match(ordercri);
        //次表
        Criteria ordercri1 = Criteria.where("groups.success").is(true);
        AggregationOperation match1 = Aggregation.match(ordercri);

        UnwindOperation unwind = Aggregation.unwind("groups");
        Aggregation aggregation = Aggregation.newAggregation(match, match1, lookupToLots, unwind);

        return mongoTemplate.aggregate(aggregation, "reClassGroup", JSONObject.class).getMappedResults();
    }

    public List<Map> MoreToOne() {
        LookupOperation lookup = LookupOperation.newLookup()
                //从表（关联的表）
                .from("studentClass")
                //主表中与从表相关联的字段
                .localField("classId")
                //从表与主表相关联的字段
                .foreignField("_id")
                //查询出的从表集合 命名
                .as("class");

        Aggregation agg = Aggregation.newAggregation(lookup);
        try {
            AggregationResults<Map> studentAggregation = mongoTemplate.aggregate(agg, "student", Map.class);
            return studentAggregation.getMappedResults();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Map> MoreToOne(Long studentId, Long classId) {
        LookupOperation lookup = LookupOperation.newLookup()
                //关联的从表名字
                .from("studentClass")
                //主表中什么字段与从表相关联
                .localField("classId")
                //从表中的什么字段与主表相关联
                .foreignField("_id")
                //自定义的从表结果集名  与主表关联的数据归于此结果集下
                .as("class");
        Criteria criteria = new Criteria();
        if (studentId != null) {
            //主表可能选择的条件
            criteria.and("_id").is(studentId);
        }
        //从表可能选择的条件
        if (classId != null) {
            //class 为我之前定义的从表结果集名
            criteria.and("class._id").is(classId);
        }
        //将筛选条件放入管道中
        MatchOperation match = Aggregation.match(criteria);
        Aggregation agg = Aggregation.newAggregation(lookup, match);
        try {
            AggregationResults<Map> studentAggregation = mongoTemplate.aggregate(agg, "student", Map.class);
            return studentAggregation.getMappedResults();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 多表一对一  以Student为主表（第一视角）
     *
     * @return
     */
    public List<Map> moreTableOneToOne() {
        //学生关联班级
        LookupOperation lookupOne = LookupOperation.newLookup()
                //关联的从表  （班级)
                .from("studentClass")
                //主表中什么字段与从表（班级)关联
                .localField("classId")
                //从表（班级）什么字段与主表关联字段对应
                .foreignField("_id")
                //从表结果集
                .as("class");

        //班级关联学校  那么此时 这两者之间 班级又是 学校的主表 班级还是学生的从表
        LookupOperation lookupTwo = LookupOperation.newLookup()
                //班级关联的从表（学校)
                .from("school")
                //主表中什么字段与从表（学校）关联  因为班级也是student从表  且已经设了结果集为class  那么主表字段也只能结果集.字段
                .localField("class.schoolId")
                .foreignField("_id")
                .as("school");

        //学校关联城市 两者之前 学校则为城市二者关联关系中的主表  学校还是班级的从表
        LookupOperation lookupThree = LookupOperation.newLookup()
                //学校关联的从表（城市)
                .from("city")
                //学校是班级的从表 且设了结果集名为school 那么要获取学校字段 也只能由之前设立的学校结果集名.字段 来获取了
                .localField("school.cityId")
                .foreignField("_id")
                .as("city");
        //将几者关联关系放入管道中 作为条件进行查询
        Aggregation aggregation = Aggregation.newAggregation(lookupOne, lookupTwo, lookupThree);
        try {
            //注意，我这里还是以student为主表  那么查询结果第一视角（最外层）还是为student
            AggregationResults<Map> aggregate = mongoTemplate.aggregate(aggregation, "student", Map.class);
            return aggregate.getMappedResults();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
