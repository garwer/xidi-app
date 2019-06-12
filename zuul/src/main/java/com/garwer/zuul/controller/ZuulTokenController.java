package com.garwer.zuul.controller;

import com.alibaba.fastjson.JSONObject;
import com.garwer.common.user.loginType.CredentialType;
import com.garwer.zuul.feign.ZuulOauth2Client;
import com.garwer.zuul.util.SystemClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.util.OAuth2Utils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Garwer
 * @Date: 2019/6/10 11:14 PM
 * @Version 1.0
 * 登录
 */

@RestController
@Slf4j
public class ZuulTokenController {

    @Autowired
    private ZuulOauth2Client zuulOauth2Client;

    @PostMapping("/sys/login")
    public Map<String, Object> login(@RequestBody JSONObject param) {
        log.info("param=>{}", param);
        String username = param.getString("username");
        String password = param.getString("password");
        Map<String, String> parameters = new HashMap<>();
        parameters.put(OAuth2Utils.GRANT_TYPE, "password");
        parameters.put(OAuth2Utils.CLIENT_ID, SystemClientUtil.CLIENT_ID);
        parameters.put("client_secret", SystemClientUtil.CLIENT_SECRET);
        parameters.put(OAuth2Utils.SCOPE, SystemClientUtil.CLIENT_SCOPE);
        //parameters.put("username", username);
        //支持多类型登录
        parameters.put("username", username + "|" + CredentialType.USERNAME.name());
        parameters.put("password", password);
        Map<String, Object> tokenInfo = zuulOauth2Client.postAccessToken(parameters);
        System.out.println("tokenInfo===" + tokenInfo);
        return tokenInfo;
    }

}
