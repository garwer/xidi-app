package com.garwer.logcenter.domain.consumer.handler;

import com.garwer.common.log.LogDto;

/**
 * Create By: LJW
 * Date: 2019/6/15
 * Time: 10:56
 * 监听消息 接收Log-stater发送的指定消息队列
 */
public interface LogHandler {
    void handler(LogDto log);
}
