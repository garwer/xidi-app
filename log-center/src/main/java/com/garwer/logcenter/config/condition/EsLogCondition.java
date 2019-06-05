package com.garwer.logcenter.config.condition;

import com.garwer.logcenter.service.LogCenterService;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.util.Objects;

/**
 * @Author: Garwer
 * @Date: 2019/6/6 12:00 AM
 * @Version 1.0
 */
public class EsLogCondition implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        return Objects.equals(context.getEnvironment().getProperty("log-center.type"), LogCenterService.LOG_CENTER_ES);
    }
}
