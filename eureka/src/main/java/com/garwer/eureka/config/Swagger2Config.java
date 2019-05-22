package com.garwer.eureka.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Author: Garwer
 * @Date: 2019/5/9 9:15 PM
 * @Version 1.0
 *
 * @EnableSwagger2启用Swagger2相关功能
 */

@Configuration
@EnableSwagger2
public class Swagger2Config {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.garwer.eureka.controller"))
                .paths(PathSelectors.regex("/swagger/.*"))
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("测试swagger Restful API")
                .description("测试 Restful API")
             //   .termsOfServiceUrl("http://127.0.0.1:8080/")
                .contact(new Contact("Garwer","13960747883@163.com","13960747883@163.com"))
                .version("1.0")
                .build();
    }
}
