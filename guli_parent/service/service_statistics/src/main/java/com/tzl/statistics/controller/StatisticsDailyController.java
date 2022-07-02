package com.tzl.statistics.controller;


import com.tzl.model.entity.StatisticsDaily;
import com.tzl.result.Result;
import com.tzl.statistics.service.StatisticsDailyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author tzl
 * @since 2022-06-08
 */
@Slf4j
@Api(tags = "统计数据接口类")
@RestController
@RequestMapping("/statistics/statisticsDaily")
public class StatisticsDailyController {

    @Autowired
    private StatisticsDailyService statisticsDailyService;

    @GetMapping("/countRegister/{day}")
    @ApiOperation("/统计某一天的网站数据，包括注册数，登录数，视频播放数，课程浏览数")
    public Result countRegister(@ApiParam(name = "day",value = "用户id") @PathVariable("day") String day){
        statisticsDailyService.countRegister(day);
        return  Result.ok();
    }

    @GetMapping("/showData/{type}/{startTime}/{endTime}")
    @ApiOperation("图标显示，返回数据和日期的数组")
    public Result showData(
            @ApiParam(name = "type",value = "用户id") @PathVariable("type") String type,
            @ApiParam(name = "startTime",value = "用户id") @PathVariable("startTime") String startTime,
            @ApiParam(name = "endTime",value = "用户id") @PathVariable("endTime") String endTime
    ){
        Map<String,Object> map = statisticsDailyService.showData(type,startTime,endTime);
        return Result.ok().data(map);
    }
}

