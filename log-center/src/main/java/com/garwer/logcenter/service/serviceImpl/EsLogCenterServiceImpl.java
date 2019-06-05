package com.garwer.logcenter.service.serviceImpl;

import com.alibaba.fastjson.JSONObject;
import com.garwer.logcenter.service.LogCenterService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * Create By: LJW
 * Date: 2019/6/5
 * Time: 17:51
 */

@Service
@Slf4j
public class EsLogCenterServiceImpl implements LogCenterService {

    @Autowired
    private TransportClient client;

    @Override
    public ResponseEntity findById(String id) {
        GetResponse response = client.prepareGet("garwer-test", "man", id).get();
        log.info(JSONObject.toJSONString(response));
        return new ResponseEntity<>(response.getSource(), HttpStatus.OK);
    }
}
