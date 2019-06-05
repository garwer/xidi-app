package com.garwer.logcenter.config;

import com.garwer.logcenter.entity.LogCenter;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
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
 *
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
    @Conditional(LogCondition.class)
    //@ConditionalOnBean
    public TransportClient getEs() {
        log.info("log-center->{}", LogCenter.LOG_TYPE_ES);
        //此处加了两个额外属性 不然报java.lang.AbstractMethodError: org.elasticsearch.transport.TcpTransport.sendMessage(Ljava/lang/Object;Lorg/elasticsearch/common/bytes/BytesReference;Ljava/lang/Runnable;)V
        Settings settings = Settings.builder().put("cluster.name", this.clusterName).put("transport.type","netty3")
                .put("http.type", "netty3").build();
        TransportClient client = new PreBuiltTransportClient(settings);
        //es客户端
        for (String clusterNode : this.clusterNodes.split(",")) {
            String ip = clusterNode.split(":")[0];
            String port = clusterNode.split(":")[1];
            try {
                client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(ip), Integer.valueOf(port)));
            } catch (UnknownHostException e) {
                log.error("获取es客户端失败", e);
            }
        }
        return client;
    }

    static class LogCondition implements Condition {
        @Override
        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
            return Objects.equals(context.getEnvironment().getProperty("log-center.type"), "es");
        }
    }

    public static void main(String[] args) throws UnknownHostException {
        //www.baidu.com/ip
        System.out.println(InetAddress.getByName("www.baidu.com"));
    }
}
