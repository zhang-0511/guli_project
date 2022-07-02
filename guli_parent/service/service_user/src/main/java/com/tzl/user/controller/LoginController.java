package com.tzl.user.controller;


import com.tzl.model.entity.UcenterMember;
import com.tzl.model.vo.LoginVo;
import com.tzl.model.vo.RegisterVo;
import com.tzl.result.Result;
import com.tzl.user.service.UcenterMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author tzl
 * @since 2022-05-26
 */
@Slf4j
@Api(tags = "用户登录注册接口类")
@RestController("userLogin")
@RequestMapping("/user/ucenterMember")
public class LoginController {

    @Autowired
    private UcenterMemberService ucenterMemberService;

    @ApiOperation("用户登录")
    @PostMapping("/login")
    public Result login( @ApiParam(name = "loginVo",value = "用户登录Vo类") @RequestBody LoginVo loginVo){
        Map<String, Object> map = ucenterMemberService.login(loginVo);
        return Result.ok().data(map);
    }

    @ApiOperation("用户注册")
    @PostMapping("/register")
    public Result register( @ApiParam(name = "registerVo",value = "用户登录注册类") @RequestBody RegisterVo registerVo){
        Map<String, Object> map = ucenterMemberService.register(registerVo);
        return Result.ok().data(map);
    }

    @ApiOperation("根据token获取用户信息")
    @GetMapping("/getUserInfo")
    public Result getUserInfo(HttpServletRequest request){
        String token = request.getHeader("token");
        if(StringUtils.isEmpty(token)) {
            return Result.ok();
        }
        UcenterMember userInfo = ucenterMemberService.getloginInfo(token);
        return Result.ok().data("userInfo",userInfo);
    }

    //根据token字符串获取用户信息
    @GetMapping("/getUserInfo/{id}")
    @ApiOperation("根据用户id获取用户信息")
    public Result getUserInfo(@ApiParam(name = "id",value = "用户id") @PathVariable("id") String id) {
        //根据用户id获取用户信息
        UcenterMember ucenterMember = ucenterMemberService.getById(id);
        return Result.ok().data("ucenterMember",ucenterMember);
    }
}

