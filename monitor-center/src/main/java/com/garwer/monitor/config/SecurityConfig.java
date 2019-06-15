package com.garwer.monitor.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Create By: LJW
 * Date: 2019/6/15
 * Time: 11:11
 * 自定义监控中心登录页配置
 */

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/loginPage.html","/index.html").permitAll() // 放开权限的url
                .anyRequest().authenticated().and()
                .formLogin().loginPage("/loginPage.html")
                .loginProcessingUrl("/authentication/form").and()
                .csrf().disable();
    }


//改为配置文件配置
//    @Override
//    public void configure(AuthenticationManagerBuilder auth) throws Exception {
//        //内置用户
//        auth.inMemoryAuthentication().passwordEncoder(passwordEncoder)
//                .withUser("linjiawei")
//                .password(passwordEncoder.encode("woshiLJW123"))
//                .roles("ADMIN");
//    }

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
