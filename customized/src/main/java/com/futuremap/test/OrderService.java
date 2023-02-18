package com.futuremap.test;

import com.alibaba.fastjson.JSONObject;
import com.futuremap.base.dictionary.DictionaryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class OrderService extends DictionaryFactory {

    @Autowired
    private MongoTemplate mongoTemplate;

    public JSONObject getOrderList(Integer page, Integer size, OrderQuery orderQuery) {
        Criteria where = new Criteria();
        Query query=new Query(where);
        long count = mongoTemplate.count(query, "user_sell");
        Pageable pageable = new PageRequest(page, size, Sort.by(Sort.DEFAULT_DIRECTION, "11")) {
        };
        JSONObject result = new JSONObject();
        result.put("total", count);
        result.put("page", page);
        result.put("size", size);
        result.put("data",mongoTemplate.find(query.with(pageable), JSONObject.class, "user_sell"));
        return result;
    }

}
