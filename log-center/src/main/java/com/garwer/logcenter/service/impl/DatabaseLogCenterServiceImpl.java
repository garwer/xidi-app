package com.garwer.logcenter.service.impl;

import com.cloud.common.log.LogDto;
import com.garwer.logcenter.config.condition.DatabaseLogCondition;
import com.garwer.logcenter.config.condition.EsLogCondition;
import com.garwer.logcenter.mapper.LogCenterMapper;
import com.garwer.logcenter.service.LogCenterService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.get.GetResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;

/**
 * @Author: Garwer
 * @Date: 2019/6/5 11:17 PM
 * @Version 1.0
 * 数据库方式
 */

@Service
@Conditional(DatabaseLogCondition.class)
@Slf4j
public class DatabaseLogCenterServiceImpl implements LogCenterService {
    @Autowired
    private LogCenterMapper logCenterMapper;

    @Override
    public void saveLog(LogDto logDto) {
        logCenterMapper.saveLog(logDto);
    }

    @Override
    public HashMap<String, String> findById(String id) {
        return null;
    }

    /**
     * 查看表是否存在,如果不存在 则新建
     */
    @PostConstruct
    public void initTable() {
        log.info("当前日志中心采用:{}", LogCenterService.LOG_CENTER_DATABASE);
        logCenterMapper.initTable();
    }
}
