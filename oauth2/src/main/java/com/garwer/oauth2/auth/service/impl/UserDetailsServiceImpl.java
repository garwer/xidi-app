package com.garwer.oauth2.auth.service.impl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.garwer.oauth2.auth.service.PermissionService;
import com.garwer.oauth2.auth.service.RoleService;
import com.garwer.oauth2.auth.service.UserService;
import com.garwer.oauth2.feign.UserClient;
import com.garwer.oauth2.util.StatusCode;
import com.garwer.usercenter.user.LoginAppUser;
import com.garwer.usercenter.user.loginType.CredentialType;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserClient userClient;

    @Autowired
    private PermissionService permissionService;

    private ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 根据用户名获取用户信息
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Result<UserVo> userResult = userService.findByUsername(username);
//        if (userResult.getCode() != StatusCode.SUCCESS_CODE) {
//            throw new UsernameNotFoundException("用户:" + username + ",不存在!");
//        }
//        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
//        boolean enabled = true; // 可用性 :true:可用 false:不可用
//        boolean accountNonExpired = true; // 过期性 :true:没过期 false:过期
//        boolean credentialsNonExpired = true; // 有效性 :true:凭证有效 false:凭证无效
//        boolean accountNonLocked = true; // 锁定性 :true:未锁定 false:已锁定
//        UserVo userVo = new UserVo();
//        BeanUtils.copyProperties(userResult.getData(),userVo);
//        Result<List<RoleVo>> roleResult = roleService.getRoleByUserId(userVo.getId());
//        if (roleResult.getCode() != StatusCode.SUCCESS_CODE){
//            List<RoleVo> roleVoList = roleResult.getData();
//            for (RoleVo role:roleVoList){
//                //角色必须是ROLE_开头，可以在数据库中设置
//                GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_"+role.getValue());
//                grantedAuthorities.add(grantedAuthority);
//                //获取权限
//                Result<List<MenuVo>> perResult  = permissionService.getRolePermission(role.getId());
//                if (perResult.getCode() != StatusCode.SUCCESS_CODE){
//                    List<MenuVo> permissionList = perResult.getData();
//                    for (MenuVo menu:permissionList
//                            ) {
//                        GrantedAuthority authority = new SimpleGrantedAuthority(menu.getCode());
//                        grantedAuthorities.add(authority);
//                    }
//                }
//            }
//        }
//        User user = new User(userVo.getUsername(), userVo.getPassword(),
//                enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, grantedAuthorities);
//        return user;
//    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 为了支持多类型登录，这里username后面拼装上登录类型,如username|type
        //简单做判断
        String[] params = username.split("\\|");
        username = params[0];// 真正的用户名
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
