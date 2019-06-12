package com.garwer.usercenter.service;

import com.alibaba.fastjson.JSONObject;
import com.garwer.usercenter.mapper.UserCenterMapper;
import com.garwer.common.user.AppUser;
import com.garwer.common.user.LoginAppUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Date;
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

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

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
        System.out.println("username=====" + username);
        AppUser appUser = userCenterMapper.findByUsername(username);
        LoginAppUser loginAppUser = new LoginAppUser();
        if (appUser != null) {
            BeanUtils.copyProperties(appUser, loginAppUser);
            System.out.println("===username===.." + appUser.getUsername());
            System.out.println("===username===.." + loginAppUser.getUsername());
            System.out.println("===loginAppUser===" + JSONObject.toJSONString(loginAppUser));
            return loginAppUser;
        }
        return null;
    }

    /**
     * 修改用户包含修改密码
     * @param appUser
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updateAppUser(AppUser appUser) {
        appUser.setUpdateTime(new Date());
        userCenterMapper.update(appUser);
        log.info("修改用户密码为：{}", appUser.getPassword());
        log.info("修改用户：{}", appUser);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updatePassword(Long userId, String oldPassword, String newPwd) {
        AppUser appUser = userCenterMapper.findByUserId(userId);
        if (StringUtils.isNoneBlank(oldPassword)) {
            if (!passwordEncoder.matches(oldPassword, appUser.getPassword())) { // 旧密码校验
                throw new IllegalArgumentException("旧密码错误");
            }
        }


    }
}
