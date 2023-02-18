package com.futuremap.salseReport.salesDaily;

import com.alibaba.fastjson.JSONObject;
import com.futuremap.base.dictionary.DictionaryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Pullwind
 */
@Service
public class SalesDailyService extends DictionaryFactory {

    @Autowired
    private MongoTemplate mongoTemplate;

    public JSONObject getInfo(SalesDailyDTO salesDailyDTO) {
        Map<String, JSONObject> globalDictionaryMap = new HashMap<>();
        List<JSONObject> globalDictionaryList = mongoTemplate.findAll(JSONObject.class, "global_dictionary");
        globalDictionaryList.forEach(e->{
            globalDictionaryMap.put(e.getString("id"), e);
        });
        return  mongoTemplate.findOne(new Query(Criteria.where("dateTime").is(salesDailyDTO.getDateTime())), JSONObject.class, "sales_daily");
    }

}
