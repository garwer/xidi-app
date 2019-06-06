package com.garwer.logcenter.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cloud.common.entity.Page;
import com.cloud.common.log.LogDto;
import com.cloud.common.utils.PageUtils;
import com.garwer.logcenter.config.condition.DatabaseLogCondition;
import com.garwer.logcenter.config.condition.EsLogCondition;
import com.garwer.logcenter.mapper.LogCenterMapper;
import com.garwer.logcenter.service.LogCenterService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author: Garwer
 * @Date: 2019/6/5 11:17 PM
 * @Version 1.0
 * 默认数据库方式
 */

@Service
@ConditionalOnExpression("'database'.equals('${log-center.type}')")
@Slf4j
public class DatabaseLogCenterServiceImpl implements LogCenterService {

    @Autowired
    private LogCenterMapper logCenterMapper;

    @Override
    public void saveLog(LogDto logDto) {
        logDto.setCreateDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        logDto.setStatus("OK");
        logCenterMapper.saveLog(logDto);
    }

    @Override
    public <T> T findLogs(JSONObject params) {
        int total = logCenterMapper.getLogDtoCount(params);
        List<LogDto> resList = Collections.emptyList();
        if (total > 0) {
            PageUtils.pageParamCheck(params);
            resList = logCenterMapper.findLogs(params);
        }
        return (T) new Page<>(total, resList);
    }


    @Override
    public HashMap<String, Object> findById(String id) {
        return logCenterMapper.findById(id);
    }

    /**
     * 查看表是否存在,如果不存在 则新建
     */
    @PostConstruct
    public void initTable() {
        log.info("当前日志中心采用【默认方式】:{}", LogCenterService.LOG_CENTER_DATABASE);
        logCenterMapper.initTable();
    }


}
