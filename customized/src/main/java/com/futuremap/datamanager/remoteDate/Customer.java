package com.futuremap.datamanager.remoteDate;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.futuremap.base.util.HttpClient;
import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class Customer extends DataService {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private DataService dataService;
    public  void getAndStore() {
        JSONObject data = new JSONObject();
//        data.put("cCusCode", "");
        data.put("pageno", 1);
        data.put("pagesize", 100);
//        data.put("startdate", "2013-01-01");
//        data.put("enddate", "");
//        getAndSave.demo("CUSTOMER", data);
        initData("CUSTOMER", data);
    }
}
