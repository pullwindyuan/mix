package org.hlpay.base.service;

import com.alibaba.fastjson.JSONObject;
import java.math.BigDecimal;
import java.util.concurrent.LinkedBlockingQueue;

public interface RedisService {
  void set(String paramString, BigDecimal paramBigDecimal);
  
  void set(String paramString, LinkedBlockingQueue<JSONObject> paramLinkedBlockingQueue) throws Exception;
  
  BigDecimal get(String paramString) throws Exception;
  
  LinkedBlockingQueue<JSONObject> getQueue(String paramString) throws Exception;
}

