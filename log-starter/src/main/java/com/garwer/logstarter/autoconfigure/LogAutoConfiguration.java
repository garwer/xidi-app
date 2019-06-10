package com.garwer.logstarter.autoconfigure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: Garwer
 * @Date: 2019/6/10 1:06 AM
 * @Version 1.0
 * 配置项 初始化一些bean操作
 */

@Configuration
public class LogAutoConfiguration {

    @Bean
    public String create() {
        System.out.println("create====" );
        return "";
    }
}
