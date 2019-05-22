package com.garwer.oauth2.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Create By: LJW
 * Date: 2019/5/7
 * Time: 14:34
 */

@RestController
@RequestMapping("/TestEndponits")
public class TestEndponits {

    @GetMapping
    public String getProduct(@PathVariable String id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return "product id:" + id;
    }

    @GetMapping("aaa")
    public String getName() {
        return "aaa";
    }
}
