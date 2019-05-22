package com.garwer.product.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Garwer
 * @Date: 2019/5/8 10:33 PM
 * @Version 1.0
 */

@RestController
@RequestMapping("/role")
public class RoleController {
    @GetMapping("getRoleByUserId/{userId}")
    public Object getRoleByUserId(@PathVariable("userId") Integer userId){
        return null;
    }
}
