package com.garwer.logcenter.service;

import com.cloud.common.log.LogDto;
import org.elasticsearch.action.get.GetResponse;


/**
 * @Author: Garwer
 * @Date: 2019/6/5 9:29 PM
 * @Version 1.0
 * 暂只提供增/查
 */

public interface LogCenterService {
    String LOG_CENTER_ES = "es";
    String LOG_CENTER_DATABASE = "database";

    /**
     * 增加日志
     */
    void saveLog(LogDto logDto);

    /**
     * 根据索引id查询日志
     * @param id
     * @return
     */
    <T> T findById(String id);

}