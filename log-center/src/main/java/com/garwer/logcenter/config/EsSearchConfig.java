package com.garwer.logcenter.config;
import com.garwer.logcenter.config.condition.EsLogCondition;
import com.garwer.logcenter.service.LogCenterService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.*;
import org.springframework.core.type.AnnotatedTypeMetadata;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Objects;

/**
 * Create By: LJW
 * Date: 2019/6/5
 * Time: 10:20
 * 日志中心
 */

@Configuration
@ConfigurationProperties(prefix = "es")
@Slf4j
public class EsSearchConfig {
    private String clusterName;

    private String clusterNodes;

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public String getClusterNodes() {
        return clusterNodes;
    }

    public void setClusterNodes(String clusterNodes) {
        this.clusterNodes = clusterNodes;
    }

    @Bean
    @Conditional(EsLogCondition.class)
    //@ConditionalOnBean
    public TransportClient getEs() {
        Settings settings = Settings.builder().put("cluster.name", this.clusterName).build();
        TransportClient client = new PreBuiltTransportClient(settings);
        //es客户端
        for (String clusterNode : this.clusterNodes.split(",")) {
            String ip = clusterNode.split(":")[0];
            String port = clusterNode.split(":")[1];
            try {
                client.addTransportAddress(new TransportAddress(InetAddress.getByName(ip), Integer.valueOf(port)));
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }
        return client;
    }


    public static void main(String[] args) throws UnknownHostException {
        System.out.println(InetAddress.getByName("www.baidu.com")); //www.baidu.com/ip
    }
}
