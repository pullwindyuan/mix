 package org.hlpay.base.mq;
 import com.rabbitmq.client.*;

 import java.io.IOException;
 import java.util.*;
 import javax.annotation.Resource;
 import org.apache.commons.lang.StringUtils;
 import org.slf4j.Logger;
 import org.slf4j.LoggerFactory;
 import org.springframework.amqp.core.AmqpTemplate;
 import org.springframework.amqp.core.Exchange;
 import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
 import org.springframework.amqp.rabbit.connection.ConnectionFactory;
 import org.springframework.amqp.rabbit.core.RabbitTemplate;
 import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
 import org.springframework.amqp.support.converter.MessageConverter;
 import org.springframework.core.env.Environment;
 import org.springframework.stereotype.Service;

 @Service
 public class RabbitConfigBase implements AddressResolver {
   Logger log = LoggerFactory.getLogger(RabbitTemplate.class);

   public static final String CALL_BACK = ":callback";

   @Resource
   private Environment env;

   private String projectName;

   List<Address> addressList;

   Address address;

   CachingConnectionFactory connectionFactory;

   @Resource
   public RabbitTemplate rabbitTemplate;

   String host;

   int port;

   String username;

   String password;

   String mqRabbitVirtualHost;

   private String CUSTOM_DIRECT_EXCHANGE_NAME;

   public static final String CUSTOM_FANOUT_EXCHANGE_NAME = "PAY-CENTER-FANOUT";

   public static Map<String, QueueConfig> QUEUE_MAP;

   public static Map<String, String> QUEUE_EXCHANGE_NAME_MAP;

   public static Map<String, Exchange> QUEUE_EXCHANGE_MAP;

   public AmqpTemplate amqpTemplate() {
     this.rabbitTemplate.setMessageConverter((MessageConverter)new Jackson2JsonMessageConverter());
     this.rabbitTemplate.setEncoding("UTF-8");

     this.rabbitTemplate.setMandatory(true);
     this.rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
           String correlationId = message.getMessageProperties().getCorrelationId();

           this.log.debug("消息：{} 发送失败, 应答码：{} 原因：{} 交换机: {}  路由键: {}", new Object[] { correlationId, Integer.valueOf(replyCode), replyText, exchange, routingKey });
         });
     this.rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
           if (ack) {
             this.log.debug("消息发送到exchange成功,id: {}", correlationData.getId());
           } else {
             this.log.debug("消息发送到exchange失败,原因: {}", cause);
           }
         });
     return this.rabbitTemplate;
   }

   public ConnectionFactory connectionFactory() {
     this.username = this.env.getProperty("spring.rabbitmq.username");
     this.password = this.env.getProperty("spring.rabbitmq.password");
     this.mqRabbitVirtualHost = this.env.getProperty("spring.rabbitmq.virtualHost");
     this.host = this.env.getProperty("spring.rabbitmq.host");
     this.port = Integer.parseInt(this.env.getProperty("spring.rabbitmq.port"));
     if (this.connectionFactory == null) {
       this.connectionFactory = new CachingConnectionFactory(this.port);
     } else {
       return this.connectionFactory;
     }
     if (StringUtils.isNotBlank(this.username)) {
       this.connectionFactory.setUsername(this.username);
       this.connectionFactory.setPassword(this.password);
     }
     this.connectionFactory.setVirtualHost(this.mqRabbitVirtualHost);
     this.address = new Address(this.host, this.port);
     this.addressList = new ArrayList<>();
     this.addressList.add(this.address);
     //this.connectionFactory.setAddressResolver(this);
     this.connectionFactory.setAddresses(this.address.getHost());
     this.connectionFactory.setPort(this.address.getPort());
     return this.connectionFactory;
   }

   public boolean init(Map<String, QueueConfig> queue_config) throws IOException {
     QUEUE_EXCHANGE_MAP = new HashMap<>();
     QUEUE_EXCHANGE_NAME_MAP = new HashMap<>();
     this.projectName = this.env.getProperty("rabbitmq.nameSpace");

     this.CUSTOM_DIRECT_EXCHANGE_NAME = this.projectName + "-DIRECT";
     if (queue_config == null) {
       return false;
     }
     QUEUE_MAP = queue_config;
     Iterator<Map.Entry<String, QueueConfig>> iterator = QUEUE_MAP.entrySet().iterator();
     this.projectName = this.env.getProperty("spring.rabbitmq.nameSpace");
     while (iterator.hasNext())
     {
       Map.Entry<String, QueueConfig> entry = iterator.next();
       String queueName = entry.getKey();
       QueueConfig config = entry.getValue();
       if (StringUtils.isNotBlank(queueName)) {
         if (config.getType() == QueueConfig.WITH_DELAY_CALLBACK.getType()) {
           QUEUE_EXCHANGE_NAME_MAP.put(queueName, "DELAY-" + this.CUSTOM_DIRECT_EXCHANGE_NAME);
           QUEUE_EXCHANGE_NAME_MAP.put(getDelayQueueName(queueName), "DELAY-" + this.CUSTOM_DIRECT_EXCHANGE_NAME);
           QUEUE_EXCHANGE_NAME_MAP.put(queueName + ":callback", "DELAY-" + this.CUSTOM_DIRECT_EXCHANGE_NAME);
         } else if (config.getType() == QueueConfig.WITH_DELAY.getType()) {
          QUEUE_EXCHANGE_NAME_MAP.put(queueName, "DELAY-" + this.CUSTOM_DIRECT_EXCHANGE_NAME);
           QUEUE_EXCHANGE_NAME_MAP.put(getDelayQueueName(queueName), "DELAY-" + this.CUSTOM_DIRECT_EXCHANGE_NAME);
         } else if (config.getType() == QueueConfig.WITH_CALLBACK.getType()) {
          QUEUE_EXCHANGE_NAME_MAP.put(queueName, this.CUSTOM_DIRECT_EXCHANGE_NAME);
           QUEUE_EXCHANGE_NAME_MAP.put(queueName + ":callback", this.CUSTOM_DIRECT_EXCHANGE_NAME);
         } else if (config.getType() == QueueConfig.NORMAL.getType()) {
           QUEUE_EXCHANGE_NAME_MAP.put(queueName, this.CUSTOM_DIRECT_EXCHANGE_NAME);
         } else if (config.getType() == QueueConfig.WITH_FANOUT.getType()) {
           QUEUE_EXCHANGE_NAME_MAP.put(queueName, "PAY-CENTER-FANOUT");
         }
       }
       if (!StringUtils.isNotBlank(queueName)) {
         break;
       }
     }  return autoConfig();
   }

   public boolean autoConfig() throws IOException {
     if (QUEUE_MAP == null) {
       return false;
     }
     Iterator<Map.Entry<String, QueueConfig>> iterator = QUEUE_MAP.entrySet().iterator();
     while (true) {
       if (!iterator.hasNext()) {
         return false;
       }

       Map.Entry<String, QueueConfig> entry = iterator.next();
       String queueName = entry.getKey();
       QueueConfig queueConfig = entry.getValue();
       if (StringUtils.isNotBlank(queueName)) {
         this.log.debug("消息队列名称: {}", queueName);
         String exchangeName = getExchange(queueName);
         Channel channel = connectionFactory().createConnection().createChannel(false);
         if (!"PAY-CENTER-FANOUT".equals(exchangeName)) {
           AMQP.Queue.DeclareOk declareOk = connectionFactory().createConnection().createChannel(false).queueDeclare(queueName, true, false, false, null);
           AMQP.Queue.BindOk bindOk = channel.queueBind(queueName, exchangeName, getQueueKey(queueName));
         }

         if (queueConfig.getType() == QueueConfig.WITH_DELAY_CALLBACK.getType() || queueConfig.getType() == QueueConfig.WITH_CALLBACK.getType()) {
           AMQP.Queue.DeclareOk declareOk = channel.queueDeclare(queueName + ":callback", true, false, false, null);
           AMQP.Queue.BindOk bindOk = channel.queueBind(queueName + ":callback", exchangeName, getQueueKey(queueName));
         }
         if (queueConfig.getType() == QueueConfig.WITH_DELAY_CALLBACK.getType() || queueConfig.getType() == QueueConfig.WITH_DELAY.getType()) {
           Map<String, Object> args = new HashMap<>(2);

           args.put("x-dead-letter-exchange", exchangeName);

           args.put("x-dead-letter-routing-key", getQueueKey(queueName));

           channel.queueDeclare(getDelayQueueName(queueName), true, false, false, args);
           channel.queueBind(getDelayQueueName(queueName), exchangeName, getDelayQueueKey(queueName), null);
           channel.queueBind(queueName, exchangeName, getQueueKey(queueName), null);
         }
       }



       if (!StringUtils.isNotBlank(queueName)) {
         return true;
       }
     }
   }
   public static QueueConfig getQueueType(String queueName) {
     return QUEUE_MAP.get(queueName);
   }

   public static String getDelayQueueKey(String queueName) {
     return "DELAY:" + queueName + "-KEY";
   }

   public static String getQueueKey(String queueName) {
     return queueName + "-KEY";
   }

   public static String getDelayQueueName(String queueName) {
     return "DELAY:" + queueName;
   }

   public static String getExchangeName(String queueName) {
     return QUEUE_EXCHANGE_NAME_MAP.get(queueName);
   }

   public String getExchange(String queueName) {
     Exchange exchange = QUEUE_EXCHANGE_MAP.get(queueName);
     if (exchange == null) {
       String exchangeName = QUEUE_EXCHANGE_NAME_MAP.get(queueName);
       if (StringUtils.isNotBlank(exchangeName)) {
         try {
           if ("PAY-CENTER-FANOUT".equals(exchangeName)) {
             connectionFactory().createConnection().createChannel(true).exchangeDeclare(exchangeName, BuiltinExchangeType.FANOUT, true);
           } else {
             connectionFactory().createConnection().createChannel(false).exchangeDeclare(exchangeName, BuiltinExchangeType.DIRECT);
           }
           return exchangeName;
         } catch (IOException e) {
           e.printStackTrace();
           return null;
         }
       }
     }
     return getExchangeName(queueName);
   }


   @Override
   public List<Address> getAddresses() throws IOException {
     return this.addressList;
   }
 }





