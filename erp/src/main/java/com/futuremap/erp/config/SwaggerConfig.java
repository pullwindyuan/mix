package com.futuremap.erp.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
//@EnableSwagger2
@ConditionalOnProperty(name = "swagger.enable", havingValue = "true")
public class SwaggerConfig {
	

    @Bean
    public Docket api() {
    	
    	
    	//可以添加多个header或参数
		/*
		 * ParameterBuilder aParameterBuilder = new ParameterBuilder();
		 * aParameterBuilder .parameterType("header") //参数类型支持header, cookie, body,
		 * query etc .name("token") //参数名 .defaultValue("token") //默认值
		 * .description("header中token字段测试") .modelRef(new ModelRef(
		 * "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJmdXR1cmVtYXBhZG1pbkBmdXR1cmVtYXAuY29tLmNuIiwidXNlcklkIjoxLCJjb21wYW55SWQiOjF9.OLBepKt4N9E0UQvAVxFrksC4mYylcS_FoWETR0fIIdc5dci5WJKhmKFSQ56M82N3UznTCVK7c1dSHeEZWNETpw"
		 * ))//指定参数值的类型 .required(false).build(); //非必需，这里是全局配置，然而在登陆的时候是不用验证的
		 * List<Parameter> aParameters = new ArrayList<Parameter>();
		 * aParameters.add(aParameterBuilder.build());
		 */
    	
    	
    	
        return new Docket(DocumentationType.SWAGGER_2).groupName("erp")
                .apiInfo(new ApiInfoBuilder()
                        .title("宜心API")
                        .description("宜心API")
                        .contact(new Contact("developer", "", "developer@futuremap.com"))
                        .version("3.0")
                        .build()
                )
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.futuremap"))
                .paths(PathSelectors.any())
                .build();
    }
    
   
    


}
