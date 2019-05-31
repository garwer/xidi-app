package com.garwer.oauth2.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
public class OAuth2Controller {

    /**
     * 登录用户信息
     * @param principal
     * @return
     */
    @GetMapping("/user-me")
    public Authentication principal(Principal principal) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("principal name ====" + principal.getName());
        return authentication;
    }


}
