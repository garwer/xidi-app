package com.garwer.nacos;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@NacosPropertySource(dataId = "springboot2-nacos-config", autoRefreshed = true)
@RestController
@EnableDiscoveryClient
public class NacosApplication {
    public static void main(String[] args) {
        SpringApplication.run(NacosApplication.class, args);
    }

    /**
     * 如果没取到配置默认为123
     */
    @NacosValue(value = "${garwer.name:garwer}", autoRefreshed = true)
    private String name;


    @GetMapping("/test")
    public String test(){
        return name;
    }
}

