package com.tzl.acl.controller;

import com.tzl.acl.service.PermissionService;
import com.tzl.model.entity.Permission;
import com.tzl.model.vo.PermissionVo;
import com.tzl.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 权限 前端控制器
 * </p>
 *
 * @author tzl
 * @since 2022-06-12
 */
@Slf4j
@Api(tags = "权限接口类")
@RestController
@RequestMapping("/acl/permission")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @GetMapping("/findAll")
    @ApiOperation("获取全部菜单")
    public Result indexAllPerisson(){
        List<PermissionVo> list = permissionService.queryAllMenu();
        return Result.ok().data("children",list);
    }

}

