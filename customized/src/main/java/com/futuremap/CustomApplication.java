package com.futuremap;

import java.io.File;
import javax.servlet.MultipartConfigElement;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

@EnableSwagger2WebMvc
@SpringBootApplication
@EnableConfigurationProperties
@ConfigurationPropertiesScan
@MapperScan({"com.futuremap.custom.mapper"})//启动类中添加这句，扫描mapper文件
//@ComponentScan("com.futuremap.bank.config.SwaggerConfig")
//@EnableMongoRepositories(basePackages = {"com.futuremap.custom.mapper.mongo"})
//@EnableMongoRepositories(basePackages = {"com.futuremap.custom.mapper.mongo"})
public class CustomApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomApplication.class, args);
	}
	
	@Bean
	public MultipartConfigElement multipartConfigElement(){
	   MultipartConfigFactory multipartConfigFactory = new MultipartConfigFactory();
	   String location = System.getProperty("user.dir") + "/data/tmp";
	   File file = new File(location);
	   if(!file.exists()){
	      file.mkdirs();
	   }
	   multipartConfigFactory.setLocation(location);
	   return multipartConfigFactory.createMultipartConfig();
	}
}
