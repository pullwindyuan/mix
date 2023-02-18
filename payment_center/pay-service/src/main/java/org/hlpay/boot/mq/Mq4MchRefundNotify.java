 package org.hlpay.boot.mq;

 import com.alibaba.fastjson.JSON;
 import com.alibaba.fastjson.JSONObject;
 import com.google.common.hash.HashCode;
 import com.rabbitmq.client.Channel;
 import java.io.IOException;
 import java.math.BigDecimal;
 import jodd.util.StringUtil;
 import org.apache.commons.lang3.StringEscapeUtils;
 import org.apache.commons.lang3.StringUtils;
 import org.hlpay.base.service.BaseService4RefundOrder;
 import org.hlpay.base.service.impl.RedisServiceImpl;
 import org.hlpay.base.service.mq.Mq4MchNotify;
 import org.hlpay.boot.mq.MqRefundWork;
 import org.hlpay.common.util.MyLog;
 import org.hlpay.common.util.RedisUtil;
 import org.springframework.amqp.core.Message;
 import org.springframework.amqp.rabbit.annotation.RabbitListener;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Service;















 @Service
 public class Mq4MchRefundNotify
   extends Mq4MchNotify
 {
   @Autowired
   private BaseService4RefundOrder baseService4RefundOrder;
   @Autowired
   private RedisServiceImpl redisService;
   @Autowired
   private MqRefundWork mqRefundWork;
   @Autowired
   private RedisUtil redisUtil;
   private static final MyLog _log = MyLog.getLog(org.hlpay.boot.mq.Mq4MchRefundNotify.class);

   public void send(String msg) {
     send("queue.notify.mch.refund", msg);
   }



   @RabbitListener(queues = {"queue.notify.mch.refund"})
   public void receive(Message message, Channel channel) throws IOException {
     String logPrefix = "【商户退款通知】";
     String msg = StringEscapeUtils.unescapeJson(new String(message.getBody()));
     String jsonStr = msg.substring(1, msg.length() - 1);
     _log.info("{}接收消息:msg={}", new Object[] { logPrefix, jsonStr });
     JSONObject msgObj = JSON.parseObject(jsonStr);
     String respUrl = msgObj.getString("url");
     String refundOrderId = msgObj.getString("orderId");
     System.out.println("=========================");
     System.out.println(msgObj.toJSONString());
     int count = msgObj.getInteger("count").intValue();
     try {
       if (StringUtils.isBlank(respUrl)) {
         _log.warn("{}商户通知URL为空,respUrl={}", new Object[] { logPrefix, respUrl });

         return;
       }
       try {
         String mchId = msgObj.getString("mchId");
         String channelId = msgObj.getString("channelId");
         String amountStr = msgObj.getString("amount");



         int payStatus = msgObj.getInteger("statues").intValue();
         if (payStatus >= 2) {
           JSONObject param1 = JSONObject.parseObject(msgObj.getString("Param1"));
           String param2 = msgObj.getString("Param2");
           param1.put("mchOrderNo", msgObj.getString("orderId"));
           param1.put("amount", msgObj.getString("amount"));
           param1.put("type", "PAY_REFUND");


           if (StringUtil.isNotBlank(param2)) {
             if (!"1".equals(param2)) {
               send("queue.notify.card", param1.toJSONString());
             }
           } else {
             send("queue.notify.card", param1.toJSONString());
           }

           _log.info("{}推送积分退款扣除奖励={}", new Object[] { logPrefix, param1.toJSONString() });
























           if (count == 0) {
             String payChannelStr = this.mqRefundWork.getChannelType(channelId);
             this.redisService.set(mchId + payChannelStr, new BigDecimal("-" + amountStr));

             _log.info("======发送需要更新的商户信息======", new Object[0]);
           }

         }
       } catch (Exception e) {
         _log.error(e, refundOrderId + "退款成功，更新redis数据失败");
       }

       String httpResult = httpPost(respUrl);

       int cnt = count + 1;
       _log.info("{}notifyCount={}", new Object[] { logPrefix, Integer.valueOf(cnt) });

       if ("success".equalsIgnoreCase(httpResult)) {

         try {
           int result = this.baseService4RefundOrder.baseUpdateStatus4Complete(refundOrderId);
           _log.info("{}refundOrderId={},退款订单状态为处理完成->{}", new Object[] { logPrefix, refundOrderId, (result == 1) ? "成功" : "失败" });
         } catch (Exception e) {
           _log.error(e, "修改退款订单状态为处理完成异常");
         }

         try {
           int result = baseUpdateMchNotifySuccess(refundOrderId, httpResult, (byte)cnt);
           _log.info("{}修改商户通知,refundOrderId={},result={},notifyCount={},结果:{}", new Object[] { logPrefix, refundOrderId, httpResult, Integer.valueOf(cnt), (result == 1) ? "成功" : "失败" });
         } catch (Exception e) {
           _log.error(e, "修改商户退款通知异常");
         }

         return;
       }
       try {
         int result = baseUpdateMchNotifyFail(refundOrderId, httpResult, (byte)cnt);
         _log.info("{}修改商户通知,refundOrderId={},result={},notifyCount={},结果:{}", new Object[] { logPrefix, refundOrderId, httpResult, Integer.valueOf(cnt), (result == 1) ? "成功" : "失败" });
       } catch (Exception e) {
         _log.error(e, "修改商户退款通知异常");
       }
       if (cnt > 5) {
         _log.info("{}通知次数notifyCount()>5,停止通知", new Object[] { respUrl, Integer.valueOf(cnt) });

         return;
       }
       msgObj.put("count", Integer.valueOf(cnt));
       send("queue.notify.mch.refund", msgObj.toJSONString(), (cnt * 60 * 1000));
       _log.info("{}发送延时通知完成,通知次数:{},{}秒后执行通知", new Object[] { respUrl, Integer.valueOf(cnt), Integer.valueOf(cnt * 60) });
     }
     finally {

       HashCode hashCode = HashCode.fromString(jsonStr);
       String key = hashCode.toString();
       this.redisUtil.delete(key);
       _log.info("删除消息发送标志：" + key, new Object[0]);
     }
   }
 }





