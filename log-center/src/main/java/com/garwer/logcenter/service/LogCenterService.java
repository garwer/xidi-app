package com.garwer.logcenter.service;

import org.springframework.http.ResponseEntity;

/**
 * Create By: LJW
 * Date: 2019/6/5
 * Time: 17:39
 */

public interface LogCenterService {
    ResponseEntity findById(String id);
}
