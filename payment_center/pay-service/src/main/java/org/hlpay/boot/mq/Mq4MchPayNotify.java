 package org.hlpay.boot.mq;

 import com.alibaba.fastjson.JSON;
 import com.alibaba.fastjson.JSONObject;
 import com.rabbitmq.client.Channel;
 import java.util.concurrent.ExecutorService;
 import javax.annotation.Resource;
 import org.apache.commons.lang3.StringUtils;
 import org.apache.commons.text.StringEscapeUtils;
 import org.hlpay.base.model.MchInfo;
 import org.hlpay.base.model.PayOrder;
 import org.hlpay.base.service.BaseService4PayOrderForCache;
 import org.hlpay.base.service.MchInfoService;
 import org.hlpay.base.service.PayChannelForPayService;
 import org.hlpay.base.service.Rest;
 import org.hlpay.base.service.impl.RedisServiceImpl;
 import org.hlpay.base.service.mq.Mq4MchNotify;
 import org.hlpay.boot.mq.MqRefundWork;
 import org.hlpay.common.util.BeanConvertUtils;
 import org.hlpay.common.util.MyLog;
 import org.hlpay.common.util.RedisProUtil;
 import org.hlpay.common.util.RedisUtil;
 import org.hlpay.common.util.StrUtil;
 import org.hlpay.common.util.ThreadExecutorUtil;
 import org.springframework.amqp.AmqpException;
 import org.springframework.amqp.core.Message;
 import org.springframework.amqp.rabbit.annotation.Exchange;
 import org.springframework.amqp.rabbit.annotation.Queue;
 import org.springframework.amqp.rabbit.annotation.QueueBinding;
 import org.springframework.amqp.rabbit.annotation.RabbitListener;
 import org.springframework.amqp.rabbit.core.RabbitTemplate;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.context.annotation.Configuration;
 import org.springframework.stereotype.Component;















 @Component
 @Configuration
 public class Mq4MchPayNotify
   extends Mq4MchNotify
 {
   @Autowired
   private BaseService4PayOrderForCache baseService4PayOrderForCache;
   @Autowired
   private RedisServiceImpl redisService;
   @Resource
   private MqRefundWork mqRefundWork;
   @Resource
   private Rest rest;
   @Autowired
   private RedisUtil redisUtil;
   private static final MyLog _log = MyLog.getLog(org.hlpay.boot.mq.Mq4MchPayNotify.class);
   String logPrefix = "【商户支付通知】";



   @Autowired
   private RedisProUtil redisProUtil;



   @Autowired
   private MchInfoService mchInfoService;



   @Autowired
   private PayChannelForPayService payChannelForPayService;



   @Autowired
   private RabbitTemplate rabbitTemplate;



   public void broadcast(String message) {
     this.rabbitTemplate.convertAndSend("PAY-CENTER-FANOUT", "", message);
   }






   @RabbitListener(bindings = {@QueueBinding(value = @Queue, exchange = @Exchange(value = "PAY-CENTER-FANOUT", type = "fanout"))})
   public void platformConfigUpdate(String payload) throws Exception {
     String msg = StrUtil.getJsonString(payload);
     _log.info("平台配置更新广播", new Object[0]);
     MchInfo mchInfo = (MchInfo)BeanConvertUtils.map2Bean(this.redisProUtil.hGetAll("platformConfig"), MchInfo.class);


     if (msg.equals("mch")) {
       this.mchInfoService.configPlatform(mchInfo);
     } else if (msg.equals("pc")) {
       this.payChannelForPayService.init(mchInfo.getExternalId());
     }
   }

   public void send(String msg) {
     _log.info("{}发送消息:msg={}", new Object[] { this.logPrefix, msg });
     send("queue.notify.mch.pay", msg);
   }

   public void send(String queueName, String msg) throws AmqpException {
     super.send(queueName, msg);
   }

   public void sendDelay(String queueName, String msg, long delay) throws AmqpException {
     send(queueName, msg, delay);
   }

   @RabbitListener(queues = {"queue.notify.mch.pay"})
   public void receive(Message message, Channel channel) {
     ExecutorService fixedThreadPool = ThreadExecutorUtil.newFixedThreadPool(8);

     fixedThreadPool.execute(() -> {
           String msg = StringEscapeUtils.unescapeJson(new String(message.getBody()));



           String jsonStr = msg.substring(1, msg.length() - 1);



           JSONObject msgObj = JSON.parseObject(jsonStr);



           String respUrl = msgObj.getString("url");


           String orderId = msgObj.getString("orderId");


           String payOrderId = msgObj.getString("orderId");


           try {
             int count = msgObj.getInteger("count").intValue();


             if (StringUtils.isBlank(respUrl)) {
               _log.warn("{}商户通知URL为空,respUrl={}", new Object[] { this.logPrefix, respUrl });


               return;
             }


             String httpResult = this.rest.postByUrl(respUrl);


             int cnt = count + 1;


             _log.info("{}notifyCount={}", new Object[] { this.logPrefix, Integer.valueOf(cnt) });


             if ("success".equalsIgnoreCase(httpResult)) {
               try {
                 PayOrder payOrder = this.baseService4PayOrderForCache.baseSelectPayOrder(payOrderId);


                 if (payOrder != null) {
                   byte status = payOrder.getStatus().byteValue();


                   if (2 == status) {
                     this.baseService4PayOrderForCache.baseUpdateStatus4Complete(orderId);
                   }
                 }
               } catch (Exception e) {
                 _log.error(e, "修改订单状态为处理完成异常");
               }



               try {
                 baseDeleteMchNotify(orderId);
               } catch (Exception e) {
                 _log.error(e, "修改商户支付通知异常");
               }


               return;
             }


             try {
               msgObj.put("count", Integer.valueOf(cnt));

               send("queue.notify.mch.pay", msgObj.toJSONString(), (cnt * 5 * 60 * 1000));
             } catch (Exception e) {
               _log.error(e, "修改商户支付通知异常");
             }
             if (cnt > 5)
               return;
           } finally {
             String key = String.valueOf(("queue.notify.mch.pay" + jsonStr).hashCode());
             this.redisUtil.delete(key);
             _log.info("删除消息发送标志：" + key, new Object[0]);
           }
         });
   }
 }





