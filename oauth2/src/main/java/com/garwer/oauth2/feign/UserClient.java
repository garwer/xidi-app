package com.garwer.oauth2.feign;
import com.garwer.usercenter.user.LoginAppUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
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
@Component
public interface UserClient {
    @GetMapping(value = "/users-anon/internal", params = "username")
    LoginAppUser findByUsername(@RequestParam("username") String username);
}
