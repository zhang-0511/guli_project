package com.tzl.client.impl;

import com.tzl.client.CourseClient;
import com.tzl.result.Result;
import org.springframework.stereotype.Component;

@Component
public class CourseClientImpl implements CourseClient {

    @Override
    public Result getCourseInfoFront(String courseId) {
        return Result.error().message("time out");
    }

    @Override
    public Result updateViewCount(String courseId) {
        return Result.error().message("time out");
    }

    @Override
    public Result updateBuyCount(String courseId) {
        return Result.error().message("time out");
    }

    @Override
    public Result getCourseCount(String day) {
        return Result.error().message("time out");
    }

    @Override
    public Result palyCount(String day) {
        return Result.error().message("time out");
    }
}
