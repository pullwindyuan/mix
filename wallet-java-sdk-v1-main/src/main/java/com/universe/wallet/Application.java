package com.universe.wallet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 使用@ComponentScan(basePackages = {"com.universe"})指定扫描路径，否则基础依赖包中的功能无法自动生效，需要手动继承才行
 * @author Pullwind
 */
@EnableSwagger2
@SpringBootApplication
@EnableConfigurationProperties
@ConfigurationPropertiesScan
@ComponentScan(basePackages = {"com.universe"})
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
