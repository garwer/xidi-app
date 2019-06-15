package com.garwer.logcenter.domain.consumer.handler.imp;

import com.garwer.common.log.LogDto;
import com.garwer.logcenter.domain.consumer.handler.LogHandler;
import org.springframework.stereotype.Component;

/**
 * Create By: LJW
 * Date: 2019/6/15
 * Time: 11:00
 * condition:日志中心采用中间件存储
 */

@Component
public class KafkaLogHandler implements LogHandler {
    @Override
    public void handler(LogDto log) {

    }
}
