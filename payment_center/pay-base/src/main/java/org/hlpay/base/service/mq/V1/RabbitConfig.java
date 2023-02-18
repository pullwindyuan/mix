 package org.hlpay.base.service.mq.V1;

 import java.io.IOException;
 import java.util.HashMap;
 import java.util.Map;
 import javax.annotation.Resource;
 import org.hlpay.base.mq.RabbitConfigBase;
 import org.springframework.amqp.core.AmqpTemplate;
 import org.springframework.amqp.rabbit.connection.ConnectionFactory;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.context.annotation.Bean;
 import org.springframework.context.annotation.Configuration;
 import org.springframework.core.env.Environment;
 import org.springframework.stereotype.Service;


 @Configuration
 @Service
 public class RabbitConfig
 {
   @Resource
   private Environment env;
   @Autowired
   private RabbitConfigBase rabbitConfigBase;

   @Bean
   public AmqpTemplate amqpTemplate() {
     return this.rabbitConfigBase.amqpTemplate();
   }


   @Bean({"connectionFactory"})
   public ConnectionFactory connectionFactory() {
     return this.rabbitConfigBase.connectionFactory();
   }

   @Bean
   public boolean init() throws IOException {
     Map<String, Boolean> queue_config = new HashMap<>();
     return this.rabbitConfigBase.init(MqCustomConfig.QUEUE_DEFINE);
   }
 }

