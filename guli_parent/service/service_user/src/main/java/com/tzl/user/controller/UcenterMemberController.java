package com.tzl.user.controller;

import com.tzl.result.Result;
import com.tzl.user.service.UcenterMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @program: guli_parent
 * @description: 用户操作前端控制层
 * @author: zl
 * @create: 2022-06-08 15:59
 **/
@Slf4j
@Api(tags = "用户操作接口类")
@RestController
@RequestMapping("/user/ucenterMember")
public class UcenterMemberController {

    @Autowired
    private UcenterMemberService ucenterMemberService;

    @GetMapping("/countRegister/{day}")
    @ApiOperation("/统计某一天的注册人数和登录人数")
    public Result countRegister(@ApiParam(name = "day",value = "用户id") @PathVariable("day") String day){
        Map<String,Object> map = ucenterMemberService.countRegister(day);
        return Result.ok().data(map);
    }

}
