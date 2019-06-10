package com.garwer.logcenter.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.garwer.common.entity.Page;
import com.garwer.common.log.LogDto;
import com.garwer.common.log.LogStatus;
import com.garwer.logcenter.service.LogCenterService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutionException;

/**
 * @Author: Garwer
 * @Date: 2019/6/5 9:30 PM
 * @Version 1.0
 * 当表达式为true时生效
 */
@Service
@ConditionalOnExpression("'es'.equals('${log-center.type}')")
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
     *
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
    public <T> T findLogs(JSONObject params) {
        if (Objects.isNull(params)) {
            return null;
        }
        SearchRequestBuilder builder = client.prepareSearch().setIndices(LOG_INDEX).setTypes(LOG_TYPE);
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();

        //用户名模糊匹配
        String username = params.getString("username");
        if (StringUtils.isNoneBlank(username)) {
            queryBuilder.must(QueryBuilders.wildcardQuery("username", "*" + username + "*"));
        }

        //模糊模糊匹配
        String module = params.getString("module");
        //用户名模糊匹配
        if (StringUtils.isNoneBlank(username)) {
            queryBuilder.must(QueryBuilders.wildcardQuery("module", "*" + module + "*"));
        }

        //状态精确匹配 只有 SUCCESS/ERROR
        String status = params.getString("status");
        if (Objects.equals(LogStatus.matchStatus(status), LogStatus.SUCCESS)) {
            queryBuilder.must(QueryBuilders.wildcardQuery("status", status));
        }

        //过滤条件
        if (queryBuilder != null) {
            builder.setPostFilter(queryBuilder);
        }

        //默认按时间降序
        Integer from = params.getInteger("from");
        if (Objects.nonNull(from)) {
            builder.setFrom(from);
        }

        Integer size = params.getInteger("size");
        if (Objects.nonNull(size)) {
            builder.setSize(size);
        }

        //log.info(queryBuilder.toString(true));
        log.info("es查询参数=>{}", JSON.toJSON(builder.toString()));

        if (queryBuilder != null) {
            builder.setPostFilter(queryBuilder);
        }

        SearchResponse searchResponse = builder.get();

        log.info("es查询结果=>{}", JSONObject.toJSON(searchResponse));

        SearchHits searchHits = searchResponse.getHits();
        Long total = searchHits.getTotalHits();

        int hitsSize = searchHits.getHits().length;
        List<LogDto> list = new ArrayList<>(hitsSize);
        if (hitsSize > 0) {
            searchHits.forEach(hit -> {
                String val = hit.getSourceAsString();
                list.add(JSONObject.parseObject(val, LogDto.class));
            });
        }
        return (T) new Page(total.intValue(), list);
    }

    public static void main(String[] args) {
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        System.out.println(JSON.toJSON(queryBuilder.toString()));
        JSONObject obj = new JSONObject();
        obj.put("query", JSON.toJSON(queryBuilder.toString(true)));
        log.info(obj.toJSONString());
    }

    @Override
    public Object findById(String id) {
        GetResponse result = client.prepareGet(LOG_INDEX, LOG_TYPE, id).get();
        log.info("es查询结果=>{}", result);
        return result.getSource();
    }

    /**
     * 搜索es日志
     *
     * @param params
     * @return
     */
    public Object findogs(JSONObject params) {
        SearchRequestBuilder builder = client.prepareSearch().setIndices(LOG_INDEX).setTypes(LOG_TYPE);
        if (Objects.isNull(params)) {
            log.info("参数为空");
            return null;
        }
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        //用户名模糊匹配
        String username = params.getString("username");
        if (StringUtils.isNoneBlank(username)) {
            queryBuilder.must(QueryBuilders.wildcardQuery("username", "*" + username + "*"));
        }
        if (queryBuilder != null) {
            builder.setPostFilter(queryBuilder);
        }

        builder.addSort("createTime", SortOrder.DESC);
        return null;
    }


}
