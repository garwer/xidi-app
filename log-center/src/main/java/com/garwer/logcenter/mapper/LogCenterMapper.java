package com.garwer.logcenter.mapper;

import com.alibaba.fastjson.JSONObject;
import com.garwer.common.log.LogDto;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

/**
 * @Author: Garwer
 * @Date: 2019/6/5 11:26 PM
 * @Version 1.0
 */

@Repository
public interface LogCenterMapper {
    void saveLog(LogDto logDto);

    void initTable();

    HashMap<String,Object> findById(String id);

    Integer getLogDtoCount(JSONObject params);

    List<LogDto> findLogs(JSONObject params);
}
