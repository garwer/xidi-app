package com.garwer.oauth2.auth.service.impl;

import com.garwer.oauth2.auth.service.RoleService;
import com.garwer.oauth2.entity.vo.Result;
import com.garwer.oauth2.entity.vo.RoleVo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Mr.Yangxiufeng
 * Date: 2018-06-13
 * Time: 11:06
 */
@Service
public class RoleServiceImpl implements RoleService {
    @Override
    public Result<List<RoleVo>> getRoleByUserId(Integer userId) {
        System.out.println("调用getRoleByUserId失败");
        return Result.failure(100,"调用getRoleByUserId失败");
    }
}
