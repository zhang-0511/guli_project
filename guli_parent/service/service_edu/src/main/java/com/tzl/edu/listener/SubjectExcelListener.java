package com.tzl.edu.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tzl.model.entity.Subject;
import com.tzl.model.excel.SubjectData;
import com.tzl.edu.service.SubjectService;
import com.tzl.exceptionHander.GuliException;
import com.tzl.result.ResultCodeEnum;

/**
 * @program: guli_parent
 * @description: 读取Excel的监听器
 * @author: zl
 * @create: 2022-05-12 19:37
 **/
public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {

    public SubjectService subjectService;

    public SubjectExcelListener() {
    }

    public SubjectExcelListener(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    //读取excel的内容，一行一行读取
    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {
        if (subjectData == null){
            throw new GuliException(ResultCodeEnum.DATA_ERROR);
        }
        //一行一行读取，每次读取有两个值，第一个值为一级分类，第二个值为二级分类
        Subject existOneSubject = this.existOneSubject(subjectService, subjectData.getOneSubjectName());
        if (existOneSubject == null) { //没有一级分类，进行添加
            existOneSubject = new Subject();
            existOneSubject.setParentId("0")
                    .setTitle(subjectData.getOneSubjectName());
            subjectService.save(existOneSubject);
        }
        //二级分类
        String pid = existOneSubject.getId();
        Subject existTwoSubject = this.existTwoSubject(subjectService, subjectData.getTwoSubjectName(), pid);
        if (existTwoSubject == null) {
            existTwoSubject = new Subject();
            existTwoSubject.setParentId(pid)
                    .setTitle(subjectData.getTwoSubjectName());
            subjectService.save(existTwoSubject);
        }

    }

    //判断一级分类是否有重复的
    private Subject existOneSubject(SubjectService service,String name){
        QueryWrapper<Subject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",name)
                .eq("parent_id",0);
        Subject one = service.getOne(wrapper);
        return one;
    }

    //判断二级分类是否有重复的
    private Subject existTwoSubject(SubjectService service,String name,String pid){
        QueryWrapper<Subject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",name)
                .eq("parent_id",pid);
        Subject one = service.getOne(wrapper);
        return one;
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
