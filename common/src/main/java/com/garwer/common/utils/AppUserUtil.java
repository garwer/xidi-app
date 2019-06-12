package com.garwer.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.garwer.common.user.LoginAppUser;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Garwer
 * @Date: 2019/6/10 3:14 PM
 * @Version 1.0
 * 获取登录用户的LoginAppUser
 */
public class AppUserUtil {
    public static LoginAppUser getLoginUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof OAuth2Authentication) {
            OAuth2Authentication oAuth2Auth = (OAuth2Authentication) authentication;
            authentication = oAuth2Auth.getUserAuthentication();
            if (authentication instanceof UsernamePasswordAuthenticationToken) {
                UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) authentication;
                Object principal = authentication.getPrincipal();
                if (principal instanceof LoginAppUser) {
                    return (LoginAppUser) principal;
                }
                HashMap map = (HashMap) authenticationToken.getDetails();
                map = (HashMap) map.get("principal");
                return JSONObject.parseObject(JSONObject.toJSONString(map), LoginAppUser.class);
            }
        }
        return null;
    }
}
