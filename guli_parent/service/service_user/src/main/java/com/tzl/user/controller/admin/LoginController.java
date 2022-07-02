package com.tzl.user.controller.admin;

import com.tzl.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * @program: guli_parent
 * @description: 用户登录前端控制层
 * @author: zl
 * @create: 2022-05-08 14:57
 **/
@Api(tags = "用户登录")
@Slf4j
@RestController("adminLogin")
@RequestMapping("/user")
public class LoginController {

    @PostMapping("/login")
    @ApiOperation("用户登录接口")
    public Result login(
            @ApiParam(name = "username",value = "用户名",required = true) String username,
            @ApiParam(name = "password",value = "密码",required = true) String password
    ){
        log.info("用户登录");
        return Result.ok().data("token","admin");
    }

    @GetMapping("/info")
    @ApiOperation("获取用户信息接口")
    public Result info(@ApiParam(name = "token",value = "token",required = true) String token){
        log.info("获取用户信息");
        return Result.ok().data("roles","[admin]").data("name","admin").data("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }
}
