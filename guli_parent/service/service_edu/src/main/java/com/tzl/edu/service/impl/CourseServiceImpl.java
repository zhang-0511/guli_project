package com.tzl.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tzl.edu.mapper.CourseMapper;
import com.tzl.edu.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tzl.exceptionHander.GuliException;
import com.tzl.model.entity.*;
import com.tzl.model.vo.*;
import com.tzl.result.ResultCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author tzl
 * @since 2022-05-13
 */
@Slf4j
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {

    @Autowired
    private CourseDescriptionService courseDescriptionService;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private ChapterService chapterService;

    @Autowired
    private VideoService videoService;


    @Override
    @Transactional(rollbackFor = {RuntimeException.class,Exception.class})
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {
        //1、向课程表中添加课程信息
        Course course = new Course();
        BeanUtils.copyProperties(courseInfoVo,course);
        course.setIsDeleted(0);
        int insert = this.baseMapper.insert(course);
        if (insert <= 0){
            //添加失败
            throw new GuliException(ResultCodeEnum.FAIL);
        }
        String courseId = course.getId();
        //2、向课程简介表中添加数据   edu_course_description
        CourseDescription description = new CourseDescription();
        description.setCourseId(courseId)
                .setId(courseId)
                .setDescription(courseInfoVo.getDescription());

        boolean save = courseDescriptionService.save(description);
        if (!save){
            throw new GuliException(ResultCodeEnum.FAIL);
        }
        return courseId;
    }

    @Override
    public CourseInfoVo getCourseInfo(String courseId) {
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        Course course = this.baseMapper.selectById(courseId);
        String subjectParentId = "";
        String subjectId = "";
        String teacherId = "";
        if (course != null) {
            BeanUtils.copyProperties(course, courseInfoVo);
            //一级信息
            subjectParentId = course.getSubjectParentId();
            subjectId = course.getSubjectId();
            teacherId = course.getTeacherId();
        }


        QueryWrapper<CourseDescription> wrapper = new QueryWrapper<>();
        wrapper.eq("id",courseId)
                .eq("course_id",courseId);
        CourseDescription description = courseDescriptionService.getOne(wrapper);
        if (description != null) {
            courseInfoVo.setDescription(description.getDescription());
        }

        Subject subjectNested = subjectService.getById(subjectParentId);
        SubjectNestedVo subjectNestedVo = new SubjectNestedVo();
        List<String> list1 = new ArrayList<>();
        if (subjectNested != null){
            BeanUtils.copyProperties(subjectNested,subjectNestedVo);
            list1.add(subjectNestedVo.getId());
        }

        //二级信息
        Subject subject = subjectService.getById(subjectId);
        List<SubjectVo> list = new ArrayList<>();
        List<SubjectNestedVo> subjectList = new ArrayList<>();

        SubjectVo subjectVo = new SubjectVo();
        if (subject != null){
            BeanUtils.copyProperties(subject,subjectVo);
            list.add(subjectVo);
            subjectNestedVo.setChildren(list);
            subjectList.add(subjectNestedVo);
            courseInfoVo.setSubjectList(subjectList);
            list1.add(subject.getId());
        }

        Teacher teacher = teacherService.getById(teacherId);
        courseInfoVo.setCascade(list1);
        courseInfoVo.setTeacher(teacher);


        QueryWrapper<Chapter> chapterWrapper = new QueryWrapper<>();
        chapterWrapper.eq("course_id",courseId)
                .orderByAsc("sort")
                .last(" limit 1 ");
        Chapter chapter = chapterService.getOne(chapterWrapper);
        if (chapter != null){
            QueryWrapper<Video> videoWrapper = new QueryWrapper<>();
            videoWrapper.eq("course_id",courseId)
                    .eq("chapter_id",chapter.getId())
                    .orderByAsc("sort")
                    .last(" limit 1 ");
            Video video = videoService.getOne(videoWrapper);
            courseInfoVo.setVideoSourceId(video.getVideoSourceId());
        }


        return courseInfoVo;
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class,Exception.class})
    public String updateCourseInfo(CourseInfoVo courseInfoVo) {
        Course course = new Course();
        BeanUtils.copyProperties(courseInfoVo,course);
        int i = this.baseMapper.updateById(course);
        if (i == 0){
            throw new GuliException(ResultCodeEnum.FAIL);
        }

        CourseDescription courseDescription = new CourseDescription();
        BeanUtils.copyProperties(courseInfoVo,courseDescription);
        boolean b = courseDescriptionService.updateById(courseDescription);
        if (!b){
            throw new GuliException(ResultCodeEnum.FAIL);
        }
        return courseInfoVo.getId();
    }

    @Override
    public CoursePublishVo getCoursePublishVoById(String courseId) {
        CoursePublishVo coursePublish = this.baseMapper.getCoursePublishVoById(courseId);
        QueryWrapper<Chapter> chapterWrapper = new QueryWrapper<>();
        chapterWrapper.eq("course_id",courseId);
        int count = chapterService.count(chapterWrapper);
        QueryWrapper<Video> videoWrapper = new QueryWrapper<>();
        videoWrapper.eq("course_id",courseId);
        int count1 = videoService.count(videoWrapper);
        coursePublish.setChapterNum(count).setVideoNum(count1);
        return coursePublish;
    }

    @Override
    public Map<String, Object> pageQuery(Long page, Long limit, CourseQuery course) {
        QueryWrapper<Course> wrapper = new QueryWrapper<>();

        String teacherId = course.getTeacherId();
        String title = course.getTitle();
        String subjectParentId = course.getSubjectParentId();
        String subjectId = course.getSubjectId();
        Integer isFree = course.getIsFree();
        String begin = course.getBegin();
        String end = course.getEnd();
        String status = course.getStatus();

        if (!StringUtils.isEmpty(teacherId)) {
            wrapper.eq("teacher_id", teacherId);
        }

        if (!StringUtils.isEmpty(title) ) {
            wrapper.like("title", title);
        }

        if (!StringUtils.isEmpty(subjectParentId)) {
            wrapper.eq("subject_parent_id", subjectParentId);
        }

        if (!StringUtils.isEmpty(subjectId)) {
            wrapper.eq("subject_id", subjectId);
        }
        if (!StringUtils.isEmpty(status)) {
            wrapper.eq("status", status);
        }

        if (!StringUtils.isEmpty(isFree)) {
            if (isFree == 0){
                wrapper.eq("price",0);
            }else if (isFree == 1){
                wrapper.ne("price", 0);
            }
        }

        if (!StringUtils.isEmpty(begin)) {
            wrapper.ge("gmt_create", begin);
        }

        if (!StringUtils.isEmpty(end)) {
            wrapper.le("gmt_create", end);
        }

        int firstIndex = Math.toIntExact((page - 1) * limit);
        int lastIndex = Math.toIntExact(page * limit);

        List<CourseVo> courseList = baseMapper.getPageCourse(firstIndex,lastIndex,course);
        int count = this.count(wrapper);

        Map<String, Object> map = new HashMap<>();
        map.put("total",count);
        map.put("rows",courseList);

        return map;
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class,Exception.class})
    public boolean removeCourseById(String courseId) {
        try{
            //根据id删除所有视频
            boolean remove = videoService.removeByCourseId(courseId);
            if (remove){
                //根据id删除所有章节
                boolean remove1 = chapterService.removeByCourseId(courseId);
                if (remove1){
                    boolean b = courseDescriptionService.removeById(courseId);
                    if (b){
                        return this.removeById(courseId);
                    }else {
                        return false;
                    }
                }else {
                    return false;
                }
            }else {
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new GuliException(ResultCodeEnum.FAIL);
        }
    }

    @Override
    public Map<String, Object> getCourseFrontList(Page<Course> pageParam, CourseQueryVo courseQueryVo) {
        Map<String, Object> map = new HashMap<>();
        QueryWrapper<Course> wrapper = new QueryWrapper<>();

        String title = courseQueryVo.getTitle();
        String teacherId = courseQueryVo.getTeacherId();
        String subjectParentId = courseQueryVo.getSubjectParentId();
        String subjectId = courseQueryVo.getSubjectId();
        String buyCountSort = courseQueryVo.getBuyCountSort();
        String gmtCreateSort = courseQueryVo.getGmtCreateSort();
        String priceSort = courseQueryVo.getPriceSort();

        if (!StringUtils.isEmpty(subjectParentId)){
            wrapper.eq("subject_parent_id",subjectParentId);
        }
        if (!StringUtils.isEmpty(title)){
            wrapper.eq("title",title);
        }
        if (!StringUtils.isEmpty(teacherId)){
            wrapper.eq("teacher_id",teacherId);
        }
        if (!StringUtils.isEmpty(subjectId)){
            wrapper.eq("subject_id",subjectId);
        }
        if (!StringUtils.isEmpty(buyCountSort)){
            wrapper.orderByDesc("buy_count");
        }
        if (!StringUtils.isEmpty(gmtCreateSort)){
            wrapper.orderByDesc("gmt_create");
        }
        if (!StringUtils.isEmpty(priceSort)){
            wrapper.orderByDesc("price");
        }

        this.page(pageParam,wrapper);

        List<Course> records = pageParam.getRecords();
        long current = pageParam.getCurrent();
        long pages = pageParam.getPages();
        long size = pageParam.getSize();
        long total = pageParam.getTotal();
        boolean hasNext = pageParam.hasNext();
        boolean hasPrevious = pageParam.hasPrevious();

        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        return map;
    }

    @Override
    public void updateViewCount(String courseId) {
        this.baseMapper.updateViewCount(courseId);
    }

    @Override
    public void updateBuyCount(String courseId) {
        this.baseMapper.updateBuyCount(courseId);
    }

    @Override
    public Map<String, Object> getCourseCount(String day) {
        Map<String, Object> map = new HashMap<>();
        Integer buyCount = this.baseMapper.getBuyCountByDay(day);
        Integer viewCount = this.baseMapper.getViewCountByDay(day);

        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        wrapper.eq("date(gmt_create)",day);
        Integer courseNum = this.baseMapper.selectCount(wrapper);

        if (buyCount == null || buyCount < 0){
            buyCount = 0;
        }
        if (viewCount == null || viewCount < 0){
            viewCount = 0;
        }
        if (courseNum == null || courseNum < 0){
            courseNum = 0;
        }
        map.put("buyCount",buyCount);
        map.put("courseNum",courseNum);
        map.put("viewCount",viewCount);
        return map;
    }
}
