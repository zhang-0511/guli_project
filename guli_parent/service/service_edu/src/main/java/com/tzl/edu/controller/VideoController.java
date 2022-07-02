package com.tzl.edu.controller;



import com.tzl.client.VodClient;
import com.tzl.model.entity.Video;
import com.tzl.model.vo.VideoInfoVo;
import com.tzl.edu.service.VideoService;
import com.tzl.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author tzl
 * @since 2022-05-13
 */
@Slf4j
@Api(tags = "课程小节接口")
@RestController
@RequestMapping("/edu/video")
public class VideoController {

    @Autowired
    private VideoService videoService;

    @Autowired
    private VodClient vodClient;

    @PostMapping("/saveVideo")
    @ApiOperation("添加课程小节信息")
    public Result saveVideo(@ApiParam(name = "videoInfoVo", value = "课程章小节信息", required = true)@RequestBody VideoInfoVo videoInfoVo){
        boolean b = videoService.saveVideoInfo(videoInfoVo);
        if (b) {
            return Result.ok();
        }else {
            return Result.error();
        }
    }

    @GetMapping("/getVideoInfo/{videoId}")
    @ApiOperation("根据课程小节ID获取课程小节信息")
    public Result getVideoInfo(@ApiParam(name = "videoId", value = "课程小节ID", required = true)@PathVariable("videoId") String videoId){
        VideoInfoVo videoInfoVo = videoService.getVideoInfoFormById(videoId);
        return Result.ok().data("videoInfoVo",videoInfoVo);
    }

    @PutMapping("/updateVideo")
    @ApiOperation("根据课程小节ID修改课程小节信息")
    public Result updateVideo(@ApiParam(name = "videoInfoVo", value = "课程章小节信息", required = true)@RequestBody VideoInfoVo videoInfoVo){
        boolean b = videoService.updateVideoInfoById(videoInfoVo);
        if (b) {
            return Result.ok();
        }else {
            return Result.error();
        }
    }

    //删除小节的时候，同时把里面的视频删掉
    @DeleteMapping("/deleteVideo/{videoId}")
    @ApiOperation("根据课程小节ID删除课程小节信息")
    public Result deleteVideo(@ApiParam(name = "videoId", value = "课程小节ID", required = true)@PathVariable("videoId") String videoId){
        Video video = videoService.getById(videoId);
        if (video != null){
            //判断小节里面是否有视频
            if (!StringUtils.isEmpty(video.getVideoSourceId())){
                Result result = vodClient.deleteAliyunVideo(video.getVideoSourceId());
                if (result.getCode() != 200){
                    return Result.error();
                }
            }
        }
        boolean b = videoService.removeById(videoId);
        if (b) {
            return Result.ok();
        }else {
            return Result.error();
        }
    }


    @PutMapping("/updatePlayCount/{videoSourceId}")
    @ApiOperation("增加视频播放量")
    public Result updatePlayCount(@ApiParam(name = "videoSourceId", value = "视频id", required = true)@PathVariable("videoSourceId") String videoSourceId){
        videoService.updatePlayCount(videoSourceId);
        return Result.ok();
    }

    @GetMapping("/palyCount/{day}")
    @ApiOperation("获取某日视频播放量")
    public Result palyCount(@ApiParam(name = "day", value = "视频id", required = true)@PathVariable("day") String day){
        Integer palyCount = videoService.palyCount(day);
        return Result.ok().data("palyCount",palyCount);
    }
}

