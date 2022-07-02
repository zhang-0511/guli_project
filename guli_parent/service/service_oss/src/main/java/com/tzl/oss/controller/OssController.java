package com.tzl.oss.controller;

import com.tzl.oss.serivce.OssService;
import com.tzl.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;

/**
 * @program: guli_parent
 * @description: 对象存储Oss前端控制层
 * @author: zl
 * @create: 2022-05-12 15:26
 **/
@Slf4j
@Api(tags = "对象存储OSS")
@RestController
@RequestMapping("/oss/fileOss")
public class OssController {

    @Autowired
    private OssService ossService;

    @PostMapping("/avatarUpload")
    @ApiOperation("/头像上传接口")
    public Result avatarUpload(MultipartFile file){
        //获取文件上传 MultipartFile
        //方法返回url
        String url = ossService.pictureUpload(file,"avatar");
        return Result.ok().data("url",url);
    }

    @PostMapping("/coverUpload")
    @ApiOperation("/课程封面上传接口")
    public Result coverUpload(MultipartFile file){
        //获取文件上传 MultipartFile
        //方法返回url
        String url = ossService.pictureUpload(file,"cover");
        return Result.ok().data("url",url);
    }

    @PostMapping("/bannerUpload")
    @ApiOperation("/轮播图上传接口")
    public Result bannerUpload(MultipartFile file){
        //获取文件上传 MultipartFile
        //方法返回url
        String url = ossService.pictureUpload(file,"banner");
        return Result.ok().data("url",url);
    }
}
