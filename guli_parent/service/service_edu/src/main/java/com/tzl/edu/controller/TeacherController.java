package com.tzl.edu.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tzl.model.entity.Teacher;
import com.tzl.edu.service.TeacherService;
import com.tzl.model.vo.TeacherVo;
import com.tzl.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author tzl
 * @since 2022-05-07
 */
@Api(tags = "讲师管理")
@Slf4j
@RestController
@RequestMapping("/edu/teacher")
@SuppressWarnings("all")
public class TeacherController {


    @Autowired
    private TeacherService teacherService;

    @GetMapping("/getTeacherList")
    @ApiOperation(value = "所有讲师列表")
    public Result getTeacherList(){
        List<Teacher> list = teacherService.list(null);
        return Result.ok().data("items",list);
    }

    @DeleteMapping("{id}")
    @ApiOperation(value = "逻辑删除讲师")
    public Result removeById(@ApiParam(name = "id",value = "讲师Id",readOnly = true) @PathVariable("id")String id){
        boolean b = teacherService.removeById(id);
        if (b){
            return Result.ok();
        }else {
            return Result.error();
        }
    }

    @GetMapping("/pageTeacher/{page}/{limit}")
    @ApiOperation(value = "分页查询讲师")
    public Result pageListTeacher(
            @ApiParam(name = "page",value = "当前页码",required = true) @PathVariable("page")Long page,
            @ApiParam(name = "limit",value = "每页记录数",required = true) @PathVariable("limit")Long limit
    ){

        Page<Teacher> pageParam = new Page<>(page, limit);

        teacherService.page(pageParam, null);

        List<Teacher> records = pageParam.getRecords();
        long total = pageParam.getTotal();

        return  Result.ok().data("total", total).data("rows", records);
    }


    @PostMapping("/pageTeacherQuery/{page}/{limit}")
    @ApiOperation(value = "分页条件查询讲师")
    public Result pageTeacherQuery(
            @ApiParam(name = "page",value = "当前页码",required = true) @PathVariable("page")Long page,
            @ApiParam(name = "limit",value = "每页记录数",required = true) @PathVariable("limit")Long limit,
            @ApiParam(name = "teacherVo",value = "条件查询实体类",required = false) @RequestBody(required = false) TeacherVo teacherVo
    ){

        Page<Teacher> pageParam = new Page<>(page, limit);

        teacherService.pageQuery(pageParam, teacherVo);

        List<Teacher> records = pageParam.getRecords();
        long total = pageParam.getTotal();

        return  Result.ok().data("total", total).data("rows", records);
    }

    @PostMapping("/addTeacher")
    @ApiOperation(value = "添加讲师")
    public Result addTeacher(
            @ApiParam(name = "teacher",value = "讲师实体类") @RequestBody Teacher teacher
    ){
        boolean save = teacherService.save(teacher);
        if (save) {
            log.info("添加讲师信息，teacher =》{}",teacher);
            return Result.ok();
        }else {
            return Result.error();
        }
    }

    @GetMapping("/findByIdTeacher/{id}")
    @ApiOperation("根据Id查询讲师")
    public Result findByIdTeacher(
            @ApiParam(name = "id",value = "讲师Id",required = true) @PathVariable("id") String id
    ){
        Teacher teacher = teacherService.getById(id);
        log.info("获取讲师信息，teacher =》{}",teacher);
        return Result.ok().data("teacher",teacher);
    }


    @PutMapping("/updateTeacher")
    @ApiOperation(value = "修改讲师")
    public Result updateTeacher(
            @ApiParam(name = "teacher",value = "讲师实体类") @RequestBody Teacher teacher
    ){
        boolean b = teacherService.updateById(teacher);

        if (b) {
            log.info("修改讲师信息，teacher =》{}",teacher);
            return Result.ok();
        }else {
            return Result.error();
        }
    }
}

