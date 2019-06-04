package com.garwer.logcenter;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
public class LogCenterApplication {
    public static void main(String[] args) {
        SpringApplication.run(LogCenterApplication.class, args);
    }
}
