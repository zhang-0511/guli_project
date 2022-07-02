package com.tzl.edu.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tzl.edu.service.TeacherService;
import com.tzl.model.entity.Teacher;
import com.tzl.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @program: guli_parent
 * @description: 前台讲师接口
 * @author: zl
 * @create: 2022-05-29 14:56
 **/
@Slf4j
@Api(tags = "前台讲师接口")
@RestController
@RequestMapping("/edu/teacherFront")
public class TeacherFrontController {

    @Autowired
    private TeacherService teacherService;

    @GetMapping("/pageTeacherFront/{page}/{limit}")
    @ApiOperation("分页查询讲师接口")
    public Result pageTeacherFront(
            @ApiParam(name = "page",value = "当前页码",required = true) @PathVariable("page")Long page,
            @ApiParam(name = "limit",value = "每页记录数",required = true) @PathVariable("limit")Long limit
    ){
        Page<Teacher> pageParam = new Page<>(page, limit);

        Map<String,Object> map = teacherService.pageTeacherFront(pageParam);

        return  Result.ok().data(map);
    }

    @GetMapping("/getTeacherInfoById/{id}")
    @ApiOperation("根据讲师id获取讲师信息和讲师所授课程接口")
    public Result getTeacherInfoById(@ApiParam(name = "id",value = "讲师id",required = true) @PathVariable("id") String id){
        Map<String, Object> map = teacherService.getTeacherInfoById(id);
        return Result.ok().data(map);
    }
}
