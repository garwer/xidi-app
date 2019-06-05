package com.garwer.logcenter.service.impl;
import com.alibaba.fastjson.JSONObject;
import com.cloud.common.log.LogDto;
import com.garwer.logcenter.config.condition.EsLogCondition;
import com.garwer.logcenter.service.LogCenterService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Conditional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

/**
 * @Author: Garwer
 * @Date: 2019/6/5 9:30 PM
 * @Version 1.0
 */
@Service
@Conditional(EsLogCondition.class)
@Slf4j
public class EsLogCenterServiceImpl implements ApplicationContextAware, LogCenterService {
    /**
     * 记录索引名为log-center
     */
    private String LOG_INDEX = "log-center";

    private String LOG_TYPE = "log";

    private static ApplicationContext applicationContext = null;

    @Autowired
    private TransportClient client;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * 索引不存在创建索引
     */
    @PostConstruct
    public void initIndex() {
        log.info("当前日志中心采用:{}", LogCenterService.LOG_CENTER_ES);
        LogCenterService logCenterService = applicationContext.getBean(LogCenterService.class);
        boolean isEs = (logCenterService instanceof EsLogCenterServiceImpl);
        if (isEs) {
            try {
                IndicesExistsResponse indicesExistsResponse = client.admin().indices()
                        .exists(new IndicesExistsRequest(LOG_INDEX)).get();
                if (indicesExistsResponse.isExists()) {
                    log.info("索引:{},已存在", LOG_INDEX);
                    return;
                }
                CreateIndexRequestBuilder requestBuilder = client.admin().indices().prepareCreate(LOG_INDEX);
                CreateIndexResponse createIndexResponse = requestBuilder.execute().actionGet();
                if (createIndexResponse.isAcknowledged()) {
                    log.info("索引:{},创建成功", LOG_INDEX);
                } else {
                    log.error("索引:{},创建失败", LOG_INDEX);
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 使用异步方式保存至es【将在独立的线程中完成，调用者无需等待该方法内操作】
     * @param logDto
     */
    @Async
    @Override
    public void saveLog(LogDto logDto) {
        logDto.setCreateDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        logDto.setStatus("OK");
        String logJson = JSONObject.toJSONString(logDto);
        log.info("log:{}", logJson);
        IndexRequestBuilder builder = client.prepareIndex(LOG_INDEX, LOG_TYPE).setSource(logJson, XContentType.JSON);
        builder.execute();
    }

    @Override
    public GetResponse findById(String id) {
        GetResponse result = client.prepareGet(LOG_INDEX, LOG_TYPE, id).get();
        return result;
    }


}
