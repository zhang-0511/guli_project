package com.tzl.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tzl.edu.service.CourseService;
import com.tzl.edu.service.IndexService;
import com.tzl.edu.service.TeacherService;
import com.tzl.model.entity.Course;
import com.tzl.model.entity.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: guli_parent
 * @description:
 * @author: zl
 * @create: 2022-05-26 12:10
 **/
@Service
public class IndexServiceImpl implements IndexService {

    @Autowired
    private CourseService courseService;

    @Autowired
    private TeacherService teacherService;

    @Override
    @Cacheable(value = "indexList",key = "'selectIndexList'")
    public Map<String, Object> indexList() {
        QueryWrapper<Teacher> teacherWrapper = new QueryWrapper<>();
        teacherWrapper.orderByDesc("sort").last("limit 4");
        List<Teacher> teacherList = teacherService.list(teacherWrapper);
        QueryWrapper<Course> courseWrapper = new QueryWrapper<>();
        courseWrapper.orderByDesc("view_count").last("limit 8");
        List<Course> courseList = courseService.list(courseWrapper);

        Map<String, Object> map = new HashMap<>();
        map.put("teacherList",teacherList);
        map.put("courseList",courseList);
        return map;
    }
}
