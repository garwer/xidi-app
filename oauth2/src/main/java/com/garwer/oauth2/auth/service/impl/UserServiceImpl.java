package com.garwer.oauth2.auth.service.impl;

import com.garwer.oauth2.auth.service.UserService;
import com.garwer.oauth2.entity.vo.Result;
import com.garwer.oauth2.entity.vo.UserVo;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {
    @Override
    public Result<UserVo> findByUsername(String username) {
        System.out.println("findByUsername调用失败");
        return Result.failure(100,"调用findByUsername接口失败");
    }
}
