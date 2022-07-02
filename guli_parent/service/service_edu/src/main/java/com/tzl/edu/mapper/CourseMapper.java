package com.tzl.edu.mapper;

import com.tzl.model.entity.Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tzl.model.vo.CoursePublishVo;
import com.tzl.model.vo.CourseQuery;
import com.tzl.model.vo.CourseVo;

import java.util.List;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author tzl
 * @since 2022-05-13
 */
public interface CourseMapper extends BaseMapper<Course> {

    public CoursePublishVo getCoursePublishVoById(String courseId);

    public List<CourseVo> getPageCourse(int firstIndex, int lastIndex, CourseQuery course);

    public void updateViewCount(String courseId);

    public void updateBuyCount(String courseId);

    Integer getBuyCountByDay(String day);

    Integer getViewCountByDay(String day);
}
