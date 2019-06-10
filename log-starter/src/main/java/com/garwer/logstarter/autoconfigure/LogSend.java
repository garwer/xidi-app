package com.garwer.logstarter.autoconfigure;

import com.garwer.common.log.LogDto;
import com.garwer.common.user.LoginAppUser;
import com.garwer.common.utils.AppUserUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @Author: Garwer
 * @Date: 2019/6/10 12:56 AM
 * @Version 1.0
 * 切面类 利用aop将LogAnnotation注解方法 发送log信息到队列中 最后日志中心将消息存储 es/database
 *
 */

@Aspect
@Component
public class LogSend {
    public LogSend() {
        logger.info("=====初始化aop=====");
    }

    /**
     * 作为starter 此处不采用lombok
     */
    private static final Logger logger = LoggerFactory.getLogger(LogSend.class);

    @Around(value = "@annotation(com.garwer.common.log.annotation.LogAnnotation)")
    public Object logSend(ProceedingJoinPoint joinPoint) {
        logger.info("aaaaaaaaaaaaaaaa");
        LogDto logDto = new LogDto();
        LoginAppUser loginAppUser = AppUserUtil.getLoginUser();
        System.out.println("获取用户名为" + loginAppUser.getUsername());
        logger.info("获取用户名为========{}", loginAppUser.getUsername() );
        if (loginAppUser != null) {
            logDto.setUsername(loginAppUser.getUsername());
        }
        return null;
    }

//    @Pointcut("execution(public * com.garwer.logstarter.autoconfigure..*.*(..))") //定义切入点
//    public void webLog() {}

}
