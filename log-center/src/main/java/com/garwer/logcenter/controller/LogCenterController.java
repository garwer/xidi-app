package com.garwer.logcenter.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.garwer.common.log.LogDto;
import com.garwer.logcenter.service.LogCenterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * @Author: Garwer
 * @Date: 2019/6/5 9:33 PM
 * @Version 1.0
 * 此处带anno可忽略权限校验
 */

@RestController
@RequestMapping("/log-center")
@Slf4j
public class LogCenterController {

    @Autowired
    private LogCenterService logCenterService; //忽略idea报红

    @PostMapping("/anon/saveLog")
    public void saveLog(@RequestBody LogDto logDto) {
        log.info("传递log=>:{}", JSON.toJSONString(logDto));
        logCenterService.saveLog(logDto);
    }

    @GetMapping("/findById")
    @PreAuthorize("hasAuthority('log:query')")
    public Object findById(@RequestParam String id) {
        return new ResponseEntity<>((HashMap) logCenterService.findById(id), HttpStatus.OK);
    }

    @PostMapping("/findLogs")
    @PreAuthorize("hasAuthority('log:query')")
    public Object findLog(@RequestBody JSONObject param) {
        return logCenterService.findLogs(param);
    }
}
