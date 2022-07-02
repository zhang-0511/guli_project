package com.tzl.client;

import com.tzl.client.impl.CourseClientImpl;
import com.tzl.result.Result;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@Repository
@FeignClient(name="service-edu",fallback = CourseClientImpl.class)
public interface CourseClient {

    @GetMapping("/edu/courseFront/getCourseInfoFront/{courseId}")
    @ApiOperation("根据课程id获取课程信息")
    public Result getCourseInfoFront(@ApiParam(name = "courseId", value = "课程ID", required = true) @PathVariable("courseId") String  courseId);

    @PutMapping("/edu/course/updateViewCount/{courseId}")
    @ApiOperation("修改课程浏览量")
    public Result updateViewCount(@ApiParam(name = "courseId", value = "课程ID", required = true) @PathVariable("courseId") String  courseId);

    @PutMapping("/edu/course/updateBuyCount/{courseId}")
    @ApiOperation("修改课程购买量")
    public Result updateBuyCount(@ApiParam(name = "courseId", value = "课程ID", required = true) @PathVariable("courseId") String  courseId);


    @GetMapping("/edu/courseFront/getCourseCount/{day}")
    @ApiOperation("获取课程每日的浏览量，购买量")
    public Result getCourseCount(@ApiParam(name = "day", value = "日期", required = true) @PathVariable("day") String  day);

    @GetMapping("/edu/video/palyCount/{day}")
    @ApiOperation("获取某日视频播放量")
    public Result palyCount(@ApiParam(name = "day", value = "视频id", required = true)@PathVariable("day") String day);
}
