package com.garwer.logcenter.controller;

import com.alibaba.fastjson.JSON;
import com.cloud.common.log.LogDto;
import com.garwer.logcenter.service.LogCenterService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.get.GetResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * @Author: Garwer
 * @Date: 2019/6/5 9:33 PM
 * @Version 1.0
 */

@RestController
@RequestMapping("/log-center")
@Slf4j
public class LogCenterController {

    @Autowired
    private LogCenterService service;

    @GetMapping("/findById")
    public Object findById(@RequestParam String id) {
        return new ResponseEntity<>((HashMap) service.findById(id), HttpStatus.OK);
    }

    @PostMapping("/saveLog")
    public void saveLog(@RequestBody LogDto logDto) {
        log.info("传递log=>:{}", JSON.toJSONString(logDto));
        service.saveLog(logDto);
    }
}
