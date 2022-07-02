package com.tzl.edu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tzl.model.entity.Teacher;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tzl.model.vo.TeacherVo;

import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author tzl
 * @since 2022-05-07
 */
public interface TeacherService extends IService<Teacher> {

    void pageQuery(Page<Teacher> pageParam, TeacherVo teacherVo);

    public Map<String, Object> pageTeacherFront(Page<Teacher> pageParam);

    public Map<String, Object> getTeacherInfoById(String id);
}
