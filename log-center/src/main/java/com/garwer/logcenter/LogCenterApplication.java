package com.garwer.logcenter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.garwer.logcenter.mapper")
public class LogCenterApplication {
    public static void main(String[] args) {
        SpringApplication.run(LogCenterApplication.class, args);
    }
}
