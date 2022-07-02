package com.tzl.vod.controller;


import com.tzl.result.Result;
import com.tzl.vod.serivce.VodService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @program: guli_parent
 * @description: 对象存储Oss前端控制层
 * @author: zl
 * @create: 2022-05-12 15:26
 **/
@Slf4j
@Api(tags = "视频上传接口")
@RestController
@RequestMapping("/vod/video")
public class VodController {

    @Autowired
   private VodService vodService;

    @PostMapping("/uploadAliyunVideo")
    @ApiOperation("视频上传到阿里云接口")
    public Result uploadAliyunVideo(MultipartFile file){
        String videoId = vodService.uploadAliyunVideo(file);
        return Result.ok().data("videoId", videoId);
    }

    @DeleteMapping("/deleteAliyunVideo/{videoSourceId}")
    @ApiOperation("删除阿里云上传的视频")
    public Result deleteAliyunVideo(@ApiParam(name = "videoSourceId", value = "阿里云视频Id", required = true)@PathVariable("videoSourceId") String videoSourceId){
        boolean b = vodService.deleteAliyunVideo(videoSourceId);
        if (b) {
            return Result.ok();
        }else {
            return Result.error();
        }
    }

    @DeleteMapping("/deleteAliyunVideoBatch")
    @ApiOperation("删除阿里云上传的视频")
    public Result deleteAliyunVideoBatch(@ApiParam(name = "videoSourceIdList", value = "阿里云视频Id集合", required = true)@RequestParam("videoSourceIdList") List<String> videoSourceIdList){
        boolean b = vodService.deleteAliyunVideoBatch(videoSourceIdList);
        if (b) {
            return Result.ok();
        }else {
            return Result.error();
        }
    }

    @GetMapping("/getPlayAuth/{videoSourceId}")
    @ApiOperation("根据视频id获取视频凭证")
    public Result getPlayAuth(@ApiParam(name = "videoSourceId", value = "阿里云视频Id", required = true)@PathVariable("videoSourceId") String videoSourceId){
        String playAuth = vodService.getPlayAuth(videoSourceId);
        return Result.ok().data("playAuth",playAuth);
    }
}
