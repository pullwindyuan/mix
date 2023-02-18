package com.futuremap.erp;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.redis.RedisReactiveHealthContributorAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@MapperScan(value = {"com.futuremap"})
@SpringBootApplication(exclude = {RedisAutoConfiguration.class,
        RedisRepositoriesAutoConfiguration.class})
@ComponentScan(value = {"com.futuremap"})
@EnableScheduling
//@EnableSwagger2
@EnableSwagger2Doc
public class FuturemapErpApplication {

    public static void main(String[] args) {
        SpringApplication.run(FuturemapErpApplication.class, args);
    }

}
