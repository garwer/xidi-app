package com.garwer.usercenter.controller;

import com.garwer.usercenter.service.UserCenterService;
import com.garwer.usercenter.user.LoginAppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Garwer
 * @Date: 2019/5/15 10:38 PM
 * @Version 1.0
 */

@RestController
public class UserController {
    @Autowired
    private UserCenterService userCenterService;

    /**
     * FeignClient中必须需要
     * @param username
     * @return
     */
    @GetMapping(value = "/users-anon/internal", params = "username")
    public LoginAppUser findByUsername(String username) {
        System.out.println("eeeeeeeeeeeee");
        return userCenterService.findByUsername(username);
    }
}
