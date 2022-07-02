package com.tzl.edu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tzl.model.entity.Course;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tzl.model.vo.CourseInfoVo;
import com.tzl.model.vo.CoursePublishVo;
import com.tzl.model.vo.CourseQuery;
import com.tzl.model.vo.CourseQueryVo;

import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author tzl
 * @since 2022-05-13
 */
public interface CourseService extends IService<Course> {

    public String saveCourseInfo(CourseInfoVo courseInfoVo);

    public CourseInfoVo getCourseInfo(String courseId);

    public String updateCourseInfo(CourseInfoVo courseInfoVo);

    public CoursePublishVo getCoursePublishVoById(String courseId);

    public Map<String, Object> pageQuery(Long page, Long limit, CourseQuery course);

    public boolean removeCourseById(String courseId);

    public Map<String, Object> getCourseFrontList(Page<Course> pageParam, CourseQueryVo courseQueryVo);

    public void updateViewCount(String courseId);

    public void updateBuyCount(String courseId);

    public Map<String, Object> getCourseCount(String day);
}
