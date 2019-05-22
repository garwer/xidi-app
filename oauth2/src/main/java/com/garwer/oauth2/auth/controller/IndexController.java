package com.garwer.oauth2.auth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Garwer
 * @Date: 2019/5/20 11:14 PM
 * @Version 1.0
 */

@RestController
public class IndexController {
    @GetMapping("/sayHello")
    private String sayHello(){
        System.out.println("Hello World");
        return "Hello World";
    }
}
