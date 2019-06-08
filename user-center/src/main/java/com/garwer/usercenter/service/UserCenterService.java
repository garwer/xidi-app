package com.garwer.usercenter.service;

import com.alibaba.fastjson.JSONObject;
import com.garwer.usercenter.mapper.UserCenterMapper;
import com.garwer.usercenter.user.AppUser;
import com.garwer.usercenter.user.LoginAppUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Objects;

/**
 * @Author: Garwer
 * @Date: 2019/5/22 11:40 PM
 * @Version 1.0
 */

@Service
@Slf4j
public class UserCenterService  {

    @Autowired
    private UserCenterMapper userCenterMapper;

    /**
     * 初始化创建用户表 如果有可删除
     */
    @PostConstruct
    public void init() {
        if(Objects.equals(userCenterMapper.initTable(), 0)) {
            log.info("app_user表已存在");
        } else {
            log.info("新建app_user成功!");
        }
    }

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
