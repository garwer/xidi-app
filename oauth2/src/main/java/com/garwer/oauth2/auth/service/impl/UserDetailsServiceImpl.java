package com.garwer.oauth2.auth.service.impl;
import com.garwer.oauth2.feign.UserClient;
import com.garwer.common.user.LoginAppUser;
import com.garwer.common.user.loginType.CredentialType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserClient userClient;

    /**
     * 根据用户名获取用户信息
     * @param username
     * @return
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 为了支持多类型登录，这里username后面拼装上登录类型,如username|type
        //简单做判断
        System.out.println("进入这里啊1。。。");
        String[] params = username.split("\\|");
        username = params[0];// 真正的用户名
        System.out.println("username1=======" + username);
        System.out.println("进入这里啊2。。。");
        LoginAppUser loginAppUser = userClient.findByUsername(username);
        System.out.println("username=======" + username);
        if (loginAppUser == null) {
            throw new AuthenticationCredentialsNotFoundException("用户不存在");
        } else if (!loginAppUser.isEnabled()) {
            throw new DisabledException("用户已作废");
        }
        if (params.length > 1) {
            // 登录类型
            CredentialType credentialType = CredentialType.valueOf(params[1]);
            System.out.println("登录类型" + credentialType);
//            if (CredentialType.PHONE == credentialType) {// 短信登录
//                handlerPhoneSmsLogin(loginAppUser, params);
//            } else if (CredentialType.WECHAT_OPENID == credentialType) {// 微信登陆
//                handlerWechatLogin(loginAppUser, params);
//            }
        }
        return loginAppUser;
    }

    public static void main(String[] args) {
        String username = "linjw|name";
        String[] params = username.split("\\|");
        username = params[0];// 真正的用户名
        System.out.println(username);
        System.out.println(params[1]);
    }
}
