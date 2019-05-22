package com.garwer.oauth2.auth.service.impl;


import com.garwer.oauth2.auth.service.PermissionService;
import com.garwer.oauth2.entity.vo.MenuVo;
import com.garwer.oauth2.entity.vo.Result;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PermissionServiceImpl implements PermissionService {
    public Result<List<MenuVo>> getRolePermission(Integer roleId) {
        System.out.println("getRolePermission调用失败");
        return Result.failure(100,"调用getRolePermission失败");
    }
}
