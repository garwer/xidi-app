package com.garwer.usercenter.service;

import com.alibaba.fastjson.JSONObject;
import com.garwer.usercenter.mapper.UserCenterMapper;
import com.garwer.usercenter.user.AppUser;
import com.garwer.usercenter.user.LoginAppUser;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: Garwer
 * @Date: 2019/5/22 11:40 PM
 * @Version 1.0
 */

@Service
public class UserCenterService  {

    @Autowired
    private UserCenterMapper userCenterMapper;

    public LoginAppUser findByUsername(String username) {
        AppUser appUser = userCenterMapper.findByUsername(username);
        LoginAppUser loginAppUser = new LoginAppUser();
        if (appUser != null) {
            BeanUtils.copyProperties(appUser, loginAppUser);
            System.out.println("===loginAppUser===" + JSONObject.toJSONString(loginAppUser));
            return loginAppUser;
        }
        return null;
    }
}
