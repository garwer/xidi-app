package com.garwer.eureka.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Garwer
 * @Date: 2019/5/9 9:07 PM
 * @Version 1.0
 * @Api：用在类上，说明该类的作用。
 *
 * @ApiOperation：注解来给API增加方法说明。
 *
 * @ApiImplicitParams : 用在方法上包含一组参数说明。
 *
 * @ApiImplicitParam：用来注解来给方法入参增加说明。
 *
 * @ApiResponses：用于表示一组响应
 *
 * @ApiResponse：用在@ApiResponses中，一般用于表达一个错误的响应信息
 */

/**
 * 有说明 && 没有说明
 * url http://localhost:8761/swagger-ui.html
 */

@RestController
@RequestMapping("/swagger")
@Api(value = "SwaggerController|一个用来测试swagger注解的控制器")
public class SwaggerController {

    @GetMapping("/getSwaggerId")
    @ApiOperation(value="获取id信息", notes="嗯嗯嗯笔记")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "ID号码", required = true, dataType = "String")
    })
    public Object test1(@RequestParam String id) {
        return id;
    }
    @GetMapping("/getSimpleId")
    public Object test(@RequestParam String id) {
        return id;
    }

}
