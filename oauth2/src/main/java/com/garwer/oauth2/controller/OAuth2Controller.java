package com.garwer.oauth2.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @Author: Garwer
 * @Date: 2019/5/15 11:31 PM
 * @Version 1.0
 * 用户信息
 */

@RestController
@RequestMapping
@Slf4j
public class OAuth2Controller {

    @Autowired
    private ConsumerTokenServices tokenServices;
    /**
     * 登录用户信息
     * @param principal
     * @return
     */
    @GetMapping("/user-me")
    public Authentication principal(Principal principal) {
        System.out.println("aaaaaaaaaaaaaaaa");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("principal name ====" + principal.getName());
        return authentication;
    }

    @DeleteMapping(value = "/remove_token", params = "access_token")
    public void removeToken(String access_token) {
        boolean flag = tokenServices.revokeToken(access_token);
        if (flag) {
            log.info("remove_token success");
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        } else {
            log.info("remove_token fail");
        }
    }
}
