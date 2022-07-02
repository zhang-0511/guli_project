package com.tzl.edu.controller;


import com.tzl.model.vo.SubjectNestedVo;
import com.tzl.edu.service.SubjectService;
import com.tzl.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author tzl
 * @since 2022-05-12
 */
@Api(tags = "课程管理")
@Slf4j
@RestController
@RequestMapping("/edu/subject")
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    @PostMapping("/addSubject")
    @ApiOperation("通过上传Excel添加课程分类")
    public Result addSubject(MultipartFile file){
        subjectService.excelUpload(file,subjectService);
        return Result.ok();
    }

    @GetMapping("/getSubjectList")
    @ApiOperation("获取课程分类集合")
    public Result getSubjectList(){
        List<SubjectNestedVo> list = subjectService.getAllOneTwoSubject();
        return Result.ok().data("list",list);
    }
}

