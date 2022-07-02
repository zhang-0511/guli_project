package com.tzl.edu.controller.front;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tzl.edu.service.ChapterService;
import com.tzl.model.entity.Course;
import com.tzl.model.vo.ChapterVo;
import com.tzl.model.vo.CourseInfoVo;
import com.tzl.edu.service.CourseService;
import com.tzl.model.vo.CourseQueryVo;
import com.tzl.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
@RequestMapping("/edu/courseFront")
public class CourseFrontController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private ChapterService chapterService;

    @PostMapping("/pageCourseFront/{page}/{limit}")
    @ApiOperation("分页查询课程章节信息")
    public Result pageCourseFront(
            @ApiParam(name = "page",value = "当前页码",required = true) @PathVariable("page")Long page,
            @ApiParam(name = "limit",value = "每页记录数",required = true) @PathVariable("limit")Long limit,
            @ApiParam(name = "courseQueryVo",value = "条件查询实体类",required = false) @RequestBody(required = false) CourseQueryVo courseQueryVo
    ){
        Page<Course> pageParam = new Page<>(page,limit);
        Map<String, Object> map = courseService.getCourseFrontList(pageParam, courseQueryVo);
        return  Result.ok().data(map);
    }

    @GetMapping("/getCourseInfoFront/{courseId}")
    @ApiOperation("根据课程id获取课程信息")
    public Result getCourseInfoFront(@ApiParam(name = "courseId", value = "课程ID", required = true) @PathVariable("courseId") String  courseId) {
        CourseInfoVo courseInfoVo = courseService.getCourseInfo(courseId);
        List<ChapterVo> chapterVoList = chapterService.getChapterVideoByCourseId(courseId);
        return Result.ok().data("courseInfo", courseInfoVo).data("chapterVoList",chapterVoList);
    }

    @GetMapping("/getCourseCount/{day}")
    @ApiOperation("获取课程每日的浏览量，购买量,新增课程数")
    public Result getCourseCount(@ApiParam(name = "day", value = "日期", required = true) @PathVariable("day") String  day) {
        Map<String, Object> map = courseService.getCourseCount(day);
        return Result.ok().data(map);
    }
}

