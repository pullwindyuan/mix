 package org.hlpay.pay.mq


 import com.alibaba.fastjson.JSON;
 import com.alibaba.fastjson.JSONObject;
 import com.rabbitmq.client.Channel;
 import java.io.IOException;
 import java.util.Date;
 import java.util.concurrent.ExecutorService;
 import javax.annotation.Resource;
 import org.apache.commons.lang.StringEscapeUtils;
 import org.hlpay.base.mapper.PayOrderMapper;
 import org.hlpay.base.model.MchInfo;
 import org.hlpay.base.model.PayOrder;
 import org.hlpay.base.service.BaseService4PayOrder;
 import org.hlpay.base.service.MchInfoService;
 import org.hlpay.base.service.NotifySettleService;
 import org.hlpay.base.service.PayChannelForPayService;
 import org.hlpay.base.service.RunModeService;
 import org.hlpay.base.service.mq.Mq4MchNotify;
 import org.hlpay.common.entity.CommonResult;
 import org.hlpay.common.enumm.ResultEnum;
 import org.hlpay.common.util.BeanConvertUtils;
 import org.hlpay.common.util.MyLog;
 import org.hlpay.common.util.RedisProUtil;
 import org.hlpay.common.util.RedisUtil;
 import org.hlpay.common.util.StrUtil;
 import org.hlpay.common.util.ThreadExecutorUtil;
 import org.hlpay.mgr.sevice.PlatformService;
 import org.hlpay.pay.service.impl.PayInnerSettle4HLServiceImpl;
 import org.springframework.amqp.core.Message;
 import org.springframework.amqp.rabbit.annotation.Exchange;
 import org.springframework.amqp.rabbit.annotation.Queue;
 import org.springframework.amqp.rabbit.annotation.QueueBinding;
 import org.springframework.amqp.rabbit.annotation.RabbitListener;
 import org.springframework.amqp.rabbit.core.RabbitTemplate;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.core.env.Environment;
 import org.springframework.stereotype.Component;




 @Component
 public class MqCardNotify
   extends Mq4MchNotify
 {
   private static final MyLog _log = MyLog.getLog(org.hlpay.pay.mq.MqCardNotify.class);

   @Resource
   private Environment env;

   @Autowired
   private PayInnerSettle4HLServiceImpl payInnerSettle4HLService;

   @Resource
   private PayOrderMapper payOrderMapper;

   @Autowired
   private NotifySettleService notifySettleService;
   @Autowired
   private RedisUtil redisUtil;
   String logPrefix = "【内部记账通知】";


   @Autowired
   private RedisProUtil redisProUtil;


   @Autowired
   private MchInfoService mchInfoService;


   @Autowired
   private PayChannelForPayService payChannelForPayService;


   @Autowired
   private RabbitTemplate rabbitTemplate;


   @Autowired
   private PlatformService platformService;


   @Autowired
   private BaseService4PayOrder baseService4PayOrder;


   @Autowired
   private RunModeService runModeService;



   public void broadcast(String message) {
     this.rabbitTemplate.convertAndSend("PAY-CENTER-FANOUT", "", message);
   }





   @RabbitListener(bindings = {@QueueBinding(value = @Queue, exchange = @Exchange(value = "PAY-CENTER-FANOUT", type = "fanout"))})
   public void platformConfigUpdate(String payload) throws Exception {
     String msg = payload;
     _log.info("平台配置更新广播:" + payload, new Object[0]);
     MchInfo mchInfo = (MchInfo)BeanConvertUtils.map2Bean(this.redisProUtil.hGetAll("platformConfig"), MchInfo.class);


     if (msg.equals("mch")) {
       this.mchInfoService.configPlatform(mchInfo);
     } else if (msg.equals("pc")) {
       this.payChannelForPayService.init(mchInfo.getExternalId());
     }
   }


   @RabbitListener(queues = {"queue.notify.platform.config.update"})
   private void ReceiveSyncConfigToPrivate(Message message, Channel channel) throws Exception {
     String msg = new String(message.getBody());
     String[] params = msg.split("-");
     if ("syncConfig".equals(params[0])) {

       this.platformService.syncConfigToPrivate(params[1]);
     } else {

       this.platformService.safeGetPlatform(params[0], params[1]);
     }
   }

   @RabbitListener(queues = {"queue.notify.mch.settle"})
   public void receiveSettle(Message message, Channel channel) {
     ExecutorService fixedThreadPool = ThreadExecutorUtil.newFixedThreadPool(8);
     fixedThreadPool.execute(() -> {
           String msg = StringEscapeUtils.unescapeJava(new String(message.getBody()));

           String jsonStr = msg.substring(1, msg.length() - 1);

           _log.info("{}记账接收消息:msg={}", new Object[] { this.logPrefix, jsonStr });

           JSONObject msgObj = JSON.parseObject(jsonStr);
           PayOrder payOrder = (PayOrder)JSONObject.toJavaObject((JSON)msgObj, PayOrder.class);
           PayOrder temp = this.payOrderMapper.selectByPrimaryKey(payOrder.getPayOrderId());
           if (temp == null) {
             this.payOrderMapper.insertSelective(payOrder);
           } else {
             this.baseService4PayOrder.baseUpdateStatus4Success(payOrder.getPayOrderId(), payOrder.getChannelOrderNo(), new Date(payOrder.getPaySuccTime().longValue()));
           }
           String orderId = msgObj.getString("payOrderId");
           try {
             try {
               this.payInnerSettle4HLService.thirdPayToIncomingOfMchBySuccessPayOrder(payOrder);
             } catch (Exception e) {
               e.printStackTrace();

               send("queue.notify.mch.settle", jsonStr, 120000L);
             }

             String channelMchId = JSONObject.parseObject(payOrder.getParam2()).getString("channelMchId");
             if ("1".equals(channelMchId)) {
               try {
                 this.notifySettleService.sendSyncPayOrderForSettle(orderId);
               } catch (NoSuchMethodException ex) {
                 ex.printStackTrace();
               }
             }
           } finally {
             String key = String.valueOf(("queue.notify.mch.settle" + jsonStr).hashCode());
             this.redisUtil.delete(key);
             _log.info("删除消息发送标志：" + key, new Object[0]);
           }
         });
   }







   public void receivePaySuccess(Message message, Channel channel) throws IOException, NoSuchMethodException {
     String msg = StringEscapeUtils.unescapeJava(new String(message.getBody()));
     String jsonStr = msg.substring(1, msg.length() - 1);
     _log.info("{}支付成功接收消息:msg={}", new Object[] { this.logPrefix, jsonStr });
     JSONObject msgObj = JSON.parseObject(jsonStr);
     PayOrder payOrder = (PayOrder)JSONObject.toJavaObject((JSON)msgObj, PayOrder.class);
     PayOrder temp = this.payOrderMapper.selectByPrimaryKey(payOrder.getPayOrderId());




     this.baseService4PayOrder.baseUpdateStatus4Success(payOrder.getPayOrderId(), payOrder.getChannelOrderNo(), new Date(payOrder.getPaySuccTime().longValue()));
   }




   @RabbitListener(queues = {"queue.notify.mch.settle.fix"})
   public void receiveSettleFix(Message message, Channel channel) throws IOException {
     String msg = StringEscapeUtils.unescapeJava(new String(message.getBody()));
     msg = msg.substring(1, msg.length() - 1);
     _log.info("{}接收消息:msg={}", new Object[] { this.logPrefix, msg });
     try {
       try {
         if (msg.equals("tryToFix")) {
           this.payInnerSettle4HLService.tryToFixSettleFailedPayOrders();
         }
       }
       catch (Exception e) {
         e.printStackTrace();
       }
     } finally {

       String key = String.valueOf(("queue.notify.mch.settle.fix" + msg).hashCode());
       this.redisUtil.delete(key);
       _log.info("删除消息发送标志：" + key, new Object[0]);
     }
   }



   @RabbitListener(queues = {"queue.notify.mch.settle.sync"})
   public void receiveSettleSync(Message message, Channel channel) {
     String msg = StringEscapeUtils.unescapeJava(new String(message.getBody()));
     int start = msg.indexOf("{");
     if (start < 0) {
       msg = "{" + msg;
     }
     int end = msg.lastIndexOf("}");
     if (end <= 0) {
       msg.concat("}");
     }
     String jsonStr = msg.substring(msg.indexOf("{"), msg.lastIndexOf("}") + 1);
     _log.info("{}记账同步接收消息:msg={}", new Object[] { this.logPrefix, jsonStr });
     try {
       try {
         CommonResult commonResult = this.payInnerSettle4HLService.syncSettle(jsonStr);
         if (commonResult.getCode().intValue() != ResultEnum.SUCCESS.getCode().intValue())
         {
           send("queue.notify.mch.settle.sync", jsonStr, 120000L);
         }
       } catch (Exception e) {
         e.printStackTrace();

         send("queue.notify.mch.settle.sync", jsonStr, 120000L);
       }
     } finally {

       String key = String.valueOf(("queue.notify.mch.settle.sync" + msg).hashCode());
       this.redisUtil.delete(key);
       _log.info("删除消息发送标志：" + key, new Object[0]);
     }
   }


   @RabbitListener(queues = {"queue.notify.mch.settle.send.sync"})
   public void sendSettleSync(Message message, Channel channel) throws IOException {
     String msg = StringEscapeUtils.unescapeJava(new String(message.getBody()));
     int start = msg.indexOf("{");
     if (start < 0) {
       msg = "{" + msg;
     }
     int end = msg.lastIndexOf("}");
     if (end <= 0) {
       msg.concat("}");
     }
     String jsonStr = msg.substring(msg.indexOf("{"), msg.lastIndexOf("}") + 1);
     _log.info("{}开始同步记账接收消息:msg={}", new Object[] { this.logPrefix, jsonStr });
     try {
       try {
         this.notifySettleService.sendSyncPayOrderForSettle((PayOrder)JSON.parseObject(jsonStr, PayOrder.class));
       } catch (Exception e) {
         e.printStackTrace();

         send("queue.notify.mch.settle.send.sync", jsonStr, 60000L);
       }
     } finally {

       String key = String.valueOf(("queue.notify.mch.settle.send.sync" + msg).hashCode());
       this.redisUtil.delete(key);
       _log.info("删除消息发送标志：" + key, new Object[0]);
     }
   }


   @RabbitListener(queues = {"queue.notify.mch.settle.batch.send.sync"})
   public void sendSettleBatchSync(Message message, Channel channel) {
     _log.info("{}批量补偿同步", new Object[0]);
     try {
       this.notifySettleService.batchSyncFailedRecords();
     } catch (NoSuchMethodException e) {
       e.printStackTrace();
     } finally {

       String key = String.valueOf("batchSync".hashCode());
       this.redisUtil.delete(key);
       _log.info("删除消息发送标志：" + key, new Object[0]);
     }
   }




   private void createPayOrder(Message message, Channel channel) throws NoSuchMethodException {
     String jsonStr = StrUtil.getJsonString(new String(message.getBody()));
     _log.info("{}创建订单接收消息:msg={}", new Object[] { this.logPrefix, jsonStr });
     PayOrder payOrder = (PayOrder)JSONObject.parseObject(jsonStr, PayOrder.class);

     try {
       this.payOrderMapper.insertSelective(payOrder);
     } catch (Exception e) {
       e.printStackTrace();
       send("queue.notify.mch.pay.order.create", jsonStr, 30000L);
     }
   }
 }

