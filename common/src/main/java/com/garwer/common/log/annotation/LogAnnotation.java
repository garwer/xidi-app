package com.garwer.common.log.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: Garwer
 * @Date: 2019/6/10 2:22 PM
 * @Version 1.0
 * 用于日志记录模块
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface LogAnnotation {
    /**
     *
     * @return
     * @see
     */
    String module();
}
