 package org.hlpay


 import com.github.xiaoymin.knife4j.spring.annotations.EnableSwaggerBootstrapUi;
 import com.spring4all.swagger.EnableSwagger2Doc;
 import org.springframework.boot.SpringApplication;
 import org.springframework.boot.autoconfigure.SpringBootApplication;
 import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
 import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
 import org.springframework.cloud.context.config.annotation.RefreshScope;
 import org.springframework.cloud.openfeign.EnableFeignClients;

 @RefreshScope
 @EnableSwagger2Doc
 @EnableSwaggerBootstrapUi
 @EnableDiscoveryClient
 @EnableCircuitBreaker
 @EnableFeignClients
 @SpringBootApplication
 public class PayMgrApplication
 {
   public static void main(String[] args) {
     SpringApplication.run(org.hlpay.PayMgrApplication.class, args);
   }
 }
