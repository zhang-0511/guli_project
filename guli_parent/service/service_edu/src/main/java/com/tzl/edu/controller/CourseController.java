package com.tzl.edu.controller;


import com.tzl.edu.service.CourseService;
import com.tzl.model.entity.Course;
import com.tzl.model.vo.CourseInfoVo;
import com.tzl.model.vo.CoursePublishVo;
import com.tzl.model.vo.CourseQuery;
import com.tzl.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author tzl
 * @since 2022-05-13
 */
@Slf4j
@Api(tags = "课程详情")
@RestController
@RequestMapping("/edu/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @PostMapping("/saveCourseInfo")
    @ApiOperation("添加课程信息")
    public Result saveCourseInfo(@ApiParam(name = "courseInfoVo", value = "课程基本信息", required = true) @RequestBody CourseInfoVo courseInfoVo) {
        String courseId = courseService.saveCourseInfo(courseInfoVo);
        return Result.ok().data("courseId", courseId);
    }

    @GetMapping("/getCourseInfo/{courseId}")
    @ApiOperation("获取课程信息")
    public Result getCourseInfo(@ApiParam(name = "courseId", value = "课程ID", required = true) @PathVariable("courseId") String  courseId) {
        CourseInfoVo courseInfoVo = courseService.getCourseInfo(courseId);
        return Result.ok().data("courseInfo", courseInfoVo);
    }

    @PutMapping("/updateCourseInfo")
    @ApiOperation("修改课程信息")
    public Result updateCourseInfo(@ApiParam(name = "courseInfoVo", value = "课程基本信息", required = true) @RequestBody CourseInfoVo courseInfoVo){
        String courseId = courseService.updateCourseInfo(courseInfoVo);
        return Result.ok().data("courseId", courseId);
    }

    @GetMapping("/getCoursePublish/{courseId}")
    @ApiOperation("根据ID获取课程发布信息")
    public Result getCoursePublishVoById(@ApiParam(name = "courseId", value = "课程ID", required = true) @PathVariable("courseId") String  courseId){
        CoursePublishVo coursePublishVo = courseService.getCoursePublishVoById(courseId);
        return Result.ok().data("CoursePublishVo",coursePublishVo);
    }

    @PutMapping("/coursePublish/{courseId}")
    @ApiOperation("最终发布课程")
    public Result coursePublish(@ApiParam(name = "courseId", value = "课程ID", required = true) @PathVariable("courseId") String  courseId){
        Course course = new Course();
        course.setId(courseId).setStatus("Normal");
        boolean b = courseService.updateById(course);
        if (b) {
            return Result.ok();
        }else {
            return Result.error();
        }
    }

    @PostMapping("/pageCourse/{page}/{limit}")
    @ApiOperation("分页查询课程章节信息")
    public Result pageCourse(
            @ApiParam(name = "page",value = "当前页码",required = true) @PathVariable("page")Long page,
            @ApiParam(name = "limit",value = "每页记录数",required = true) @PathVariable("limit")Long limit,
            @ApiParam(name = "course",value = "条件查询实体类",required = false) @RequestBody(required = false) CourseQuery course
    ){

        Map<String, Object> map = courseService.pageQuery(page, limit, course);
        return  Result.ok().data(map);
    }

    @DeleteMapping("/deleteCourseById/{courseId}")
    @ApiOperation("根据课程Id删除章节小节课程")
    public Result removeCourse(@ApiParam(name = "courseId", value = "课程ID", required = true) @PathVariable("courseId") String  courseId){
        boolean b = courseService.removeCourseById(courseId);
        if (b) {
            return Result.ok();
        }else {
            return Result.error();
        }
    }

    @PutMapping("/updateViewCount/{courseId}")
    @ApiOperation("修改课程浏览量")
    public Result updateViewCount(@ApiParam(name = "courseId", value = "课程ID", required = true) @PathVariable("courseId") String  courseId){
        courseService.updateViewCount(courseId);
        return Result.ok();
    }

    @PutMapping("/updateBuyCount/{courseId}")
    @ApiOperation("修改课程购买量")
    public Result updateBuyCount(@ApiParam(name = "courseId", value = "课程ID", required = true) @PathVariable("courseId") String  courseId){
        courseService.updateBuyCount(courseId);
        return Result.ok();
    }
}

