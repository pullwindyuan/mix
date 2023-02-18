package com.futuremap.datamanager.remoteDate;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

@Service
public class SaleType extends DataService {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private DataService dataService;
    public  void getAndStore() {
        JSONObject data = new JSONObject();
        data.put("pageno", 1);
        data.put("pagesize", 100);
//        getAndSave.demo("SALE_TYPE", data);
        initData("SALE_TYPE", data);
    }
}
