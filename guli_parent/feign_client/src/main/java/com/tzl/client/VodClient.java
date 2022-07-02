package com.tzl.client;

import com.tzl.client.impl.VodFileDegradeFeignClient;
import com.tzl.result.Result;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @program: guli_parent
 * @description: fegin调用
 * @author: zl
 * @create: 2022-05-22 14:26
 **/
@Repository
@FeignClient(name = "service-vod", fallback = VodFileDegradeFeignClient.class)
public interface VodClient {

    @DeleteMapping("/vod/video/deleteAliyunVideo/{videoSourceId}")
    @ApiOperation("删除阿里云上传的视频")
    public Result deleteAliyunVideo(@ApiParam(name = "videoSourceId", value = "阿里云视频Id", required = true)@PathVariable("videoSourceId") String videoSourceId);


    @DeleteMapping("/vod/video/deleteAliyunVideoBatch")
    @ApiOperation("删除阿里云上传的视频")
    public Result deleteAliyunVideoBatch(@ApiParam(name = "videoSourceIdList", value = "阿里云视频Id集合", required = true)@RequestParam("videoSourceIdList") List<String> videoSourceIdList);
}
