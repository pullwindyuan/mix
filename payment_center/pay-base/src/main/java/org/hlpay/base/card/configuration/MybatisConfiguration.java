 package org.hlpay.base.card.configuration;

 import com.github.pagehelper.PageHelper;
 import java.util.Properties;
 import org.springframework.context.annotation.Bean;
 import org.springframework.context.annotation.Configuration;




 @Configuration
 public class MybatisConfiguration
 {
   @Bean
   public PageHelper pageHelper() {
     PageHelper pageHelper = new PageHelper();
     Properties properties = new Properties();
     properties.setProperty("offsetAsPageNum", "true");
     properties.setProperty("rowBoundsWithCount", "true");
     properties.setProperty("reasonable", "true");
     properties.setProperty("dialect", "mysql");
     pageHelper.setProperties(properties);
     return pageHelper;
   }
 }

