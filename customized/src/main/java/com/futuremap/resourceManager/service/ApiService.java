package com.futuremap.resourceManager.service;

import com.alibaba.fastjson.JSONObject;
import com.futuremap.base.dictionary.DictionaryFactory;
import com.futuremap.base.util.HttpClient;
import com.futuremap.test.OrderQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ApiService extends DictionaryFactory {

    @Autowired
    private MongoTemplate mongoTemplate;

    private JSONObject apiConfig;

    public JSONObject getApiConfig(String path) {
        apiConfig = mongoTemplate.findById(path, JSONObject.class, "user_template");
        return apiConfig;
    }

    public JSONObject requestApiAdapter(String path, JSONObject request) {
        JSONObject dataQueryForestStr = mongoTemplate.findOne(new Query(Criteria.where("id").is(path)), JSONObject.class, "api");

        JSONObject data = new JSONObject();
        data.put("cInvCode", "");
        data.put("pageno", 1);
        data.put("pagesize", 10);
        data.put("startdate", "2013-01-01");
        data.put("enddate", "");
        Map<String, String> params = new HashMap<>();

        params.put("data", data.toJSONString());
        return JSONObject.parseObject(HttpClient.post(params, path));
    }
}
