package com.garwer.oauth2.auth.service;


import com.garwer.oauth2.auth.service.impl.PermissionServiceImpl;
import com.garwer.oauth2.entity.vo.MenuVo;
import com.garwer.oauth2.entity.vo.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "mss-upms",fallback = PermissionServiceImpl.class)
public interface PermissionService {
    @GetMapping("permission/getRolePermission/{roleId}")
    Result<List<MenuVo>> getRolePermission(@PathVariable("roleId") Integer roleId);
}
