 package org.hlpay.base.mq;

 import java.util.UUID;
 import javax.annotation.Resource;
 import org.springframework.amqp.AmqpException;
 import org.springframework.amqp.core.Message;
 import org.springframework.amqp.core.MessagePostProcessor;
 import org.springframework.amqp.core.MessageProperties;
 import org.springframework.amqp.rabbit.connection.CorrelationData;
 import org.springframework.stereotype.Service;

 @Service
 public class Mq4Base
 {
   @Resource
   private RabbitConfigBase rabbitConfigBase;

   public void send(String queueName, String msg) throws AmqpException {
     this.rabbitConfigBase.rabbitTemplate.convertAndSend(queueName, msg);
   }

   public void sendBack(String queueName, String msg) {
     this.rabbitConfigBase.rabbitTemplate.convertAndSend(queueName + ":callback", msg);
   }

   public void send(String queue_name, String msg, long delay) throws AmqpException {
     CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());

     MessagePostProcessor messagePostProcessor = message -> {
         MessageProperties messageProperties = message.getMessageProperties();

         messageProperties.setContentEncoding("utf-8");

         messageProperties.setExpiration(delay + "");

         return message;
       };

     this.rabbitConfigBase.rabbitTemplate.convertAndSend(RabbitConfigBase.getExchangeName(queue_name), RabbitConfigBase.getDelayQueueKey(queue_name), msg, messagePostProcessor, correlationData);
   }
 }

