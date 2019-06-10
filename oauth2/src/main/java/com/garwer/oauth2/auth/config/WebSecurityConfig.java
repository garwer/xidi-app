package com.garwer.oauth2.auth.config;

import com.garwer.common.constants.PermitAllUrl;
import com.garwer.oauth2.auth.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//Warning:(20, 52) java: org.springframework.security.crypto.password中的org.springframework.security.crypto.password.NoOpPasswordEncoder已过时

/**
 * 认证中心校验
 * 用户认证
 */
@Configuration
//@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(PermitAllUrl.permitAllUrl()).permitAll() // 放开权限的url
                .anyRequest().authenticated().and()
                .httpBasic().and().csrf().disable();
    }

    //Spring Security 升级到5版本后密码支持多种加密格式；
    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
          //内置用户
//        auth.inMemoryAuthentication().passwordEncoder(passwordEncoder)
//                .withUser("user")
//                .password(passwordEncoder.encode("123"))
//                .roles("ADMIN");

        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * 加密测试
     * @param args
     */
    public static void main(String[] args) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encode = bCryptPasswordEncoder.encode("linjw");
        System.out.println(encode);
    }

    /***
     * 取消过滤的资源
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**", "/js/**", "/plugins/**", "/favicon.ico");
    }
}
