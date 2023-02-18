 package org.hlpay.base.card.configuration;

 import javax.annotation.Resource;
 import org.hlpay.base.card.interceptor.RequestInterceptor;
 import org.springframework.context.annotation.Configuration;
 import org.springframework.web.servlet.HandlerInterceptor;
 import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
 import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

 @Configuration
 public class RequestConfiguration
   extends WebMvcConfigurerAdapter
 {
   @Resource
   private RequestInterceptor requestInterceptor;

   public void addInterceptors(InterceptorRegistry registry) {
     registry.addInterceptor((HandlerInterceptor)this.requestInterceptor).addPathPatterns(new String[] { "/api/saCard/save" });
     super.addInterceptors(registry);
   }
 }





