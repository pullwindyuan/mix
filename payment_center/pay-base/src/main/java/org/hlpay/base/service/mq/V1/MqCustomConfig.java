 package org.hlpay.base.service.mq.V1;

 import java.util.HashMap;
 import java.util.Map;
 import javax.annotation.Resource;
 import org.hlpay.base.mq.QueueConfig;

 @Resource
 public class MqCustomConfig
 {
   public static final String MCH_PAY_NOTIFY_QUEUE_NAME = "queue.notify.mch.pay";
   public static final String MCH_TRANS_NOTIFY_QUEUE_NAME = "queue.notify.mch.trans";
   public static final String MCH_REFUND_NOTIFY_QUEUE_NAME = "queue.notify.mch.refund";
   public static final String CARD_NOTIFY_QUEUE_NAME = "queue.notify.card";
   public static final String CARD_SETTLE_INDEPENDENT_WITHDRAW_QUEUE_NAME = "queue.notify.mch.settle.independent.withdraw";
   public static final String CARD_SETTLE_QUEUE_NAME = "queue.notify.mch.settle";
   public static final String CARD_SETTLE_WRITE_QUEUE_NAME = "queue.notify.mch.settle.write";
   public static final String CARD_SETTLE_FIX_QUEUE_NAME = "queue.notify.mch.settle.fix";
   public static final String CARD_SETTLE_SYNC_QUEUE_NAME = "queue.notify.mch.settle.sync";
   public static final String CARD_SETTLE_SEND_SYNC_QUEUE_NAME = "queue.notify.mch.settle.send.sync";
   public static final String CARD_SETTLE_SEND_BATCH_SYNC_QUEUE_NAME = "queue.notify.mch.settle.batch.send.sync";
   public static final String CARD_SETTLE_CALLBACK_COMPLETED_QUEUE_NAME = "queue.notify.mch.settle.completed.callback";
   public static final String CARD_SETTLE_CALLBACK_CASH_COMPLETED_QUEUE_NAME = "queue.notify.mch.cash.settle.completed.callback";
   public static final String PLATFORM_CONFIG_QUEUE_NAME = "queue.notify.platform.config.update";
   public static final String MCH_INIT_PAY_ORDER = "queue.notify.mch.init.pay.order";
   public static final String MCH_CREATE_PAY_ORDER = "queue.notify.mch.pay.order.create";
   public static final String MCH_PAY_SUCCESS = "queue.notify.mch.pay.success";
   public static final String MCH_PAY_CONCURRENCY_TEST = "queue.notify.mch.pay.concurrency.test";
   public static final String TEST_QUEUE_NAME = "queue.notify.test";
   public static final Map<String, QueueConfig> QUEUE_DEFINE = new HashMap<>(); static {
     QUEUE_DEFINE.put("queue.notify.card", QueueConfig.NORMAL);
     QUEUE_DEFINE.put("queue.notify.mch.pay", QueueConfig.WITH_DELAY);
     QUEUE_DEFINE.put("queue.notify.mch.trans", QueueConfig.WITH_DELAY);
     QUEUE_DEFINE.put("queue.notify.mch.refund", QueueConfig.WITH_DELAY);
     QUEUE_DEFINE.put("queue.notify.mch.settle", QueueConfig.WITH_DELAY);

     QUEUE_DEFINE.put("queue.notify.mch.settle.independent.withdraw", QueueConfig.WITH_DELAY);
     QUEUE_DEFINE.put("queue.notify.mch.settle.sync", QueueConfig.WITH_DELAY);
     QUEUE_DEFINE.put("queue.notify.mch.settle.send.sync", QueueConfig.WITH_DELAY);
     QUEUE_DEFINE.put("queue.notify.mch.settle.batch.send.sync", QueueConfig.WITH_DELAY);
     QUEUE_DEFINE.put("queue.notify.mch.settle.fix", QueueConfig.WITH_DELAY);
     QUEUE_DEFINE.put("queue.notify.mch.settle.completed.callback", QueueConfig.WITH_DELAY);
     QUEUE_DEFINE.put("queue.notify.mch.cash.settle.completed.callback", QueueConfig.WITH_DELAY);
     QUEUE_DEFINE.put("queue.notify.platform.config.update", QueueConfig.WITH_DELAY);
     QUEUE_DEFINE.put("queue.notify.mch.init.pay.order", QueueConfig.NORMAL);


     QUEUE_DEFINE.put("queue.notify.mch.pay.concurrency.test", QueueConfig.WITH_DELAY);
     QUEUE_DEFINE.put("queue.notify.test", QueueConfig.WITH_DELAY);
   }
 }

