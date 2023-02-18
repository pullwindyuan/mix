 package org.hlpay.boot.mq;

 import com.alibaba.fastjson.JSONObject;
 import java.math.BigDecimal;
 import java.util.concurrent.LinkedBlockingQueue;
 import org.apache.commons.lang3.StringUtils;
 import org.hlpay.base.service.RefundQueueWork;
 import org.hlpay.base.service.impl.RedisServiceImpl;
 import org.hlpay.common.entity.CommonResult;
 import org.hlpay.common.entity.Result;
 import org.hlpay.common.enumm.ResultEnum;
 import org.hlpay.common.util.HLPayUtil;
 import org.hlpay.common.util.MyLog;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Service;




 @Service
 public class MqRefundWork
   extends RefundQueueWork
 {
   private static final MyLog _log = MyLog.getLog(org.hlpay.boot.mq.MqRefundWork.class);

   @Autowired
   private RedisServiceImpl redisService;

   String logPrefix = "【异步退款处理】";

   private String key = "queue";











































   public CommonResult<Result> peekQueue(String payChannel) {
     LinkedBlockingQueue<JSONObject> lbq = null;
     try {
       lbq = readPro(payChannel);
     } catch (Exception e) {
       e.printStackTrace();
       return HLPayUtil.makeRetFail(HLPayUtil.makeRetMap("FAIL", "执行退款失败，退款队列初始化失败", null, null));
     }
     _log.info(">>>>>>>>>> peekQueue()搜索是否有需要退款的队列开始 <<<<<<<<<<", new Object[0]);

     String mchId = "";
     String mchAmount = "";

     if (lbq == null) {
       return HLPayUtil.makeRetFail(HLPayUtil.makeRetMap("FAIL", "无退款队列", null, null));
     }
     try {
       if (lbq.size() > 0) {
         _log.info("本地队列长度：" + lbq.size(), new Object[0]);
         JSONObject jo = lbq.peek();
         mchId = jo.getString("mchId");

         _log.info("KEY：{}", new Object[] { mchId + payChannel });
         BigDecimal dailyIncoming = this.redisService.get(mchId + payChannel);
         _log.info("dailyIncoming：{}", new Object[] { dailyIncoming.toString() });
         String refundAmount = jo.getString("amount");
         BigDecimal amount = new BigDecimal(refundAmount);
         _log.info("退款金额：{}", new Object[] { amount });
         if (dailyIncoming.compareTo(amount) >= 0) {

           this.redisService.set(mchId + payChannel, new BigDecimal("-" + refundAmount));
           lbq.poll();
           _log.info("peekQueue()可退款,本地队列剩余长度：" + lbq.size(), new Object[0]);
           CommonResult<Result> retStr = payOrderWork(jo.toString());
           if (retStr.getCode() == ResultEnum.SUCCESS.getCode());



           return retStr;
         }
       } else {
         _log.info("没有需要退款的用户", new Object[0]);
       }
     } catch (Exception e) {
       e.printStackTrace();
     } finally {
       try {
         _log.info("本地队列长度：" + lbq.size(), new Object[0]);
         this.redisService.set(this.key + payChannel, lbq);
         lbq.clear();
         _log.info(">>>>>>>>>> peekQueue()搜索是否有需要退款的队列结束 <<<<<<<<<<", new Object[0]);
       } catch (Exception e) {
         e.printStackTrace();
       }
     }
     return null;
   }

   public CommonResult<Result> addRefund(JSONObject jo) {
     LinkedBlockingQueue<JSONObject> lbq = null;
     _log.info(">>>>>>>>>> refund()搜索是否有需要退款的队列开始 <<<<<<<<<<", new Object[0]);

     String mchId = "";
     String channelId = "";

     String payChannel = "";
     try {
       channelId = jo.getString("channelId");
       payChannel = getChannelType(channelId);
       _log.info("获取redis队列payChannel：" + payChannel, new Object[0]);
       lbq = readPro(payChannel);
       _log.info("获取redis队列相比之后的长度：" + lbq.size(), new Object[0]);
       lbq.add(jo);
       if (lbq.size() == 1) {
         _log.info("本地队列长度：" + lbq.size(), new Object[0]);
         jo = lbq.peek();
         mchId = jo.getString("mchId");
         BigDecimal dailyIncoming = this.redisService.get(mchId + payChannel);
         _log.info("dailyIncoming：{}", new Object[] { dailyIncoming.toString() });
         String refundAmount = jo.getString("amount");
         BigDecimal amount = new BigDecimal(refundAmount);
         if (dailyIncoming.compareTo(amount) >= 0) {

           lbq.poll();
           CommonResult<Result> retStr = payOrderWork(jo.toString());
           _log.info("refund()可退款", new Object[0]);
           if (retStr.getCode() == ResultEnum.SUCCESS.getCode()) {
             this.redisService.set(mchId + payChannel, new BigDecimal("-" + refundAmount));
           }
           return retStr;
         }
       }
     } catch (Exception e) {
       e.printStackTrace();
     } finally {
       try {
         _log.info("本地队列长度" + lbq.size(), new Object[0]);
         this.redisService.set(this.key + payChannel, lbq);
         lbq.clear();
         _log.info(">>>>>>>>>> refund()搜索是否有需要退款的队列结束 <<<<<<<<<<", new Object[0]);
       } catch (Exception e) {
         e.printStackTrace();
       }
     }
     return null;
   }

   public LinkedBlockingQueue readPro(String payChannel) throws Exception {
     _log.info("获取redis队列KEY：{}", new Object[] { this.key + payChannel });
     LinkedBlockingQueue<JSONObject> redisLbq = this.redisService.getQueue(this.key + payChannel);
     if (redisLbq != null) {
       _log.info("获取本地队列长度：" + redisLbq.size(), new Object[0]);
       _log.info("获取redis队列长度：" + redisLbq.size(), new Object[0]);
     }
     return redisLbq;
   }

   public String getChannelType(String channelId) {
     if (StringUtils.isBlank(channelId)) {
       return "";
     }
     String[] strarr = channelId.split("_");
     return strarr[0];
   }
 }

