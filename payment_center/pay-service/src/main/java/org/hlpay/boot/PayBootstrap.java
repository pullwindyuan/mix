 package org.hlpay.boot


 import org.hlpay.base.channel.unionpay.sdk.SDKConfig;
 import org.hlpay.common.util.MyLog;
 import org.springframework.boot.SpringApplication;
 import org.springframework.boot.autoconfigure.SpringBootApplication;
 import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
 import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
 import org.springframework.cloud.context.config.annotation.RefreshScope;
 import org.springframework.cloud.openfeign.EnableFeignClients;
 import org.springframework.context.annotation.ComponentScan;





 @RefreshScope
 @EnableDiscoveryClient
 @EnableCircuitBreaker
 @EnableFeignClients
 @SpringBootApplication
 @ComponentScan(basePackages = {"org.hlpay"})
 public class PayBootstrap
 {
   private static MyLog _log = MyLog.getLog(org.hlpay.boot.PayBootstrap.class);





   public static void main(String[] args) {
     SpringApplication.run(org.hlpay.boot.PayBootstrap.class, args);

     SDKConfig.getConfig().loadPropertiesFromSrc();

     _log.info("###### 支付项目启动 ######", new Object[0]);
   }
 }

