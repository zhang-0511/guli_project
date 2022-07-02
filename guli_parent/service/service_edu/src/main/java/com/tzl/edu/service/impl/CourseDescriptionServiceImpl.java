package com.tzl.edu.service.impl;

import com.tzl.model.entity.CourseDescription;
import com.tzl.edu.mapper.CourseDescriptionMapper;
import com.tzl.edu.service.CourseDescriptionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程简介 服务实现类
 * </p>
 *
 * @author tzl
 * @since 2022-05-13
 */
@Service
public class CourseDescriptionServiceImpl extends ServiceImpl<CourseDescriptionMapper, CourseDescription> implements CourseDescriptionService {

}
