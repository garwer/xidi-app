package com.garwer.logcenter.controller;

import com.alibaba.fastjson.JSONObject;
import com.garwer.logcenter.config.EsSearchConfig;
import com.garwer.logcenter.service.LogCenterService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.get.GetResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Create By: LJW
 * Date: 2019/6/5
 * Time: 17:27
 * TransportClient工具类查询
 */

@RestController
@RequestMapping("/es")
@Slf4j
public class LogCenterController {

    @Autowired
    private LogCenterService logCenterService;

    /**
     * 查询
     */
    @GetMapping("/garwer")
    public ResponseEntity get(@RequestParam String id) {
        return logCenterService.findById(id);

    }


}
