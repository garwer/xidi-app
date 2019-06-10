package com.garwer.usercenter.controller;
import com.garwer.common.log.annotation.LogAnnotation;
import com.garwer.common.user.AppUser;
import com.garwer.common.utils.AppUserUtil;
import com.garwer.usercenter.service.UserCenterService;
import com.garwer.common.user.LoginAppUser;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: Garwer
 * @Date: 2019/5/15 10:38 PM
 * @Version 1.0
 */

@RestController
public class UserController {

//    @Autowired
//    private ConsumerTokenServices tokenServices;

    @Autowired
    private UserCenterService userCenterService;

    /**
     * FeignClient中必须需要参数
     * @param username
     * @return
     */
    @GetMapping(value = "/users-anon/internal", params = "username")
    public LoginAppUser findByUsername(String username) {
        return userCenterService.findByUsername(username);
    }

    @DeleteMapping(value = "/remove_token", params = "access_token")
    public void removeToken(String access_token) {

    }

    @LogAnnotation(module = "修改个人信息")
    @PutMapping("/users/me")
    public AppUser updateMe(@RequestBody AppUser appUser) {
        System.out.println("appUser=" + appUser);
        AppUser user = AppUserUtil.getLoginUser();
        appUser.setUserId(user.getUserId());
        userCenterService.updateAppUser(appUser);
        return appUser;
    }

    @LogAnnotation(module = "修改密码")
    @PutMapping(value = "/users/password", params = {"oldPassword", "newPassword"})
    public void updatePassword(String oldPassword, String newPassword) {
        if (StringUtils.isBlank(oldPassword)) {
            throw new IllegalArgumentException("旧密码不能为空");
        }
        if (StringUtils.isBlank(newPassword)) {
            throw new IllegalArgumentException("新密码不能为空");
        }
        AppUser user = AppUserUtil.getLoginUser();
        userCenterService.updatePassword(user.getUserId(), oldPassword, newPassword);
    }

    @LogAnnotation(module = "测试aop")
    @GetMapping(value = "/users-anon/aaa")
    public LoginAppUser testAop() {
        System.out.println("testAop");
        return null;
    }

}
