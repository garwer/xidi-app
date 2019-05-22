package com.garwer.oauth2.auth.controller;

import com.garwer.oauth2.entity.vo.Result;
import com.garwer.oauth2.util.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LogoutController {
    @Autowired
    private ConsumerTokenServices consumerTokenServices;

    @DeleteMapping(value = "/exit")
    public @ResponseBody
    Result revokeToken(String access_token){
        Result msg = new Result();
        if (consumerTokenServices.revokeToken(access_token)){
            msg.setCode(StatusCode.SUCCESS_CODE);
            msg.setMsg("注销成功");
        }else {
            msg.setCode(StatusCode.FAILURE_CODE);
            msg.setMsg("注销失败");
        }
        return msg;
    }
}
