package com.garwer.oauth2.feign;
import com.garwer.usercenter.user.LoginAppUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.Map;

/**
 * @Author: Garwer
 * @Date: 2019/5/15 10:29 PM
 * @Version 1.0
 * user-center 用户中心id
 */

@FeignClient("user-center")
public interface UserClient {
    @GetMapping(value = "/user-anon/findByUsername", params = "username")
    LoginAppUser findByUsername(@RequestParam("userName") String userName);
}
