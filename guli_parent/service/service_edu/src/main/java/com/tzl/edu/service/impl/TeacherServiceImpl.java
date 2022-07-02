package com.tzl.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tzl.edu.service.CourseService;
import com.tzl.model.entity.Course;
import com.tzl.model.entity.Teacher;
import com.tzl.edu.mapper.TeacherMapper;
import com.tzl.edu.service.TeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tzl.model.vo.TeacherVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author tzl
 * @since 2022-05-07
 */
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {

    @Autowired
    private CourseService courseService;

    @Override
    public void pageQuery(Page<Teacher> pageParam, TeacherVo teacherQuery) {

        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("gmt_create","sort");

        if (teacherQuery == null){
            baseMapper.selectPage(pageParam, queryWrapper);
            return;
        }

        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();

        if (!StringUtils.isEmpty(name)) {
            queryWrapper.like("name", name);
        }

        if (!StringUtils.isEmpty(level) ) {
            queryWrapper.eq("level", level);
        }

        if (!StringUtils.isEmpty(begin)) {
            queryWrapper.ge("gmt_create", begin);
        }

        if (!StringUtils.isEmpty(end)) {
            queryWrapper.le("gmt_create", end);
        }

        baseMapper.selectPage(pageParam, queryWrapper);
    }

    @Override
    public Map<String, Object> pageTeacherFront(Page<Teacher> pageParam) {
        Map<String, Object> map = new HashMap<>();

        QueryWrapper<Teacher> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("sort");
        this.baseMapper.selectPage(pageParam,wrapper);

        List<Teacher> teacherList = pageParam.getRecords();
        long current = pageParam.getCurrent();
        long pages = pageParam.getPages();
        long size = pageParam.getSize();
        long total = pageParam.getTotal();
        boolean hasNext = pageParam.hasNext();
        boolean hasPrevious = pageParam.hasPrevious();

        map.put("items", teacherList);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        return map;
    }

    @Override
    public Map<String, Object> getTeacherInfoById(String id) {
        Map<String, Object> map = new HashMap<>();
        Teacher teacher = this.baseMapper.selectById(id);
        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("buy_count")
                .eq("teacher_id",id);
        List<Course> courseList = courseService.list(wrapper);
        map.put("teacher",teacher);
        map.put("courseList",courseList);
        return map;
    }
}
