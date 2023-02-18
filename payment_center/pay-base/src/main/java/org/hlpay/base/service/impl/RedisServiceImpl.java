 package org.hlpay.base.service.impl;

 import com.alibaba.fastjson.JSONObject;
 import java.math.BigDecimal;
 import java.time.LocalDate;
 import java.util.concurrent.LinkedBlockingQueue;
 import javax.annotation.Resource;
 import org.hlpay.base.service.RedisService;
 import org.hlpay.common.util.DateUtils;
 import org.hlpay.common.util.MyLog;
 import org.springframework.context.annotation.Primary;
 import org.springframework.data.redis.core.HashOperations;
 import org.springframework.data.redis.core.RedisTemplate;
 import org.springframework.data.redis.core.ValueOperations;
 import org.springframework.stereotype.Service;

 @Service
 @Primary
 public class RedisServiceImpl
   implements RedisService
 {
   private static final MyLog _log = MyLog.getLog(RedisServiceImpl.class);

   @Resource
   RedisTemplate<String, LinkedBlockingQueue> redisTemplate;

   public void set(String key, BigDecimal value) {
     HashOperations<String, String, Integer> vo = this.redisTemplate.opsForHash();
     LocalDate localDate = LocalDate.now();
     if (this.redisTemplate.opsForHash().hasKey(localDate.toString(), key).booleanValue()) {
       _log.info("累加收款金额:" + value.doubleValue(), new Object[0]);
       vo.increment(localDate.toString(), key, value.intValue());
     } else {
       _log.info("插入收款金额:" + value.doubleValue(), new Object[0]);
       vo.put(localDate.toString(), key, Integer.valueOf(value.intValue()));
       vo.getOperations().expireAt(localDate.toString(), DateUtils.getDayLastDate(localDate));
     }
   }


   public BigDecimal get(String key) {
     try {
       LocalDate localDate = LocalDate.now();
       HashOperations<String, String, Object> vo = this.redisTemplate.opsForHash();
       if (vo != null &&
         vo.get(localDate.toString(), key) != null) {
         Long value = (Long)vo.get(localDate.toString(), key);
         if (value != null) {
           return new BigDecimal(value.longValue());
         }
         return new BigDecimal("0");
       }

       BigDecimal init = new BigDecimal("0");
       set(key, init);
       return init;
     } catch (Exception e) {
       e.printStackTrace();
       return new BigDecimal("0");
     }
   }


   public LinkedBlockingQueue<JSONObject> getQueue(String key) {
     try {
       ValueOperations<String, LinkedBlockingQueue> vo = this.redisTemplate.opsForValue();
       return (LinkedBlockingQueue<JSONObject>)vo.get(key);
     } catch (Exception e) {
       e.printStackTrace();

       return null;
     }
   }

   public void set(String key, LinkedBlockingQueue<JSONObject> value) {
     ValueOperations<String, LinkedBlockingQueue> vo = this.redisTemplate.opsForValue();
     if (this.redisTemplate.hasKey(key).booleanValue()) {
       this.redisTemplate.delete(key);
       vo.set(key, value);
     } else {
       vo.set(key, value);
     }
   }
 }
