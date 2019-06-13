package com.garwer.monitor;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableAdminServer
@SpringBootApplication
@EnableDiscoveryClient
public class MonitorCenterApplication {
    public static void main(String[] args) {
        SpringApplication.run(MonitorCenterApplication.class, args);
    }
}

