package com.futuremap.datamanager.remoteDate;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

@Service
public class SalesOrder extends DataService {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private DataService dataService;
    public  void getAndStore() {
        JSONObject data = new JSONObject();
//        data.put("cSOCode", "");
        data.put("pageno", 1);
        data.put("pagesize", 100);
//        data.put("startdate", "2013-01-01");
//        data.put("enddate", "");
//        getAndSave.demo("SALES_ORDER", data);
        initData("SALES_ORDER", data);
    }
}
