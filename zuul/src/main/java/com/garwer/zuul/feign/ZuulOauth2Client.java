package com.garwer.zuul.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @Author: Garwer
 * @Date: 2019/6/10 11:15 PM
 * @Version 1.0
 */

@FeignClient("oauth2")
@Component
public interface ZuulOauth2Client {

    /**
     * 网关登录入口 用于返回token等信息
     * @param param
     * @return
     */
    @PostMapping(path = "/oauth/token")
    Map<String, Object> postAccessToken(@RequestParam Map<String, String> param);

    /**
     * 注销移除token用
     * @param access_token
     */
    @DeleteMapping(path = "/remove_token")
    void removeToken(@RequestParam("access_token") String access_token);
}
