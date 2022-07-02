package com.tzl.statistics.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tzl.client.CourseClient;
import com.tzl.client.UcenterClient;
import com.tzl.model.entity.StatisticsDaily;
import com.tzl.result.Result;
import com.tzl.statistics.mapper.StatisticsDailyMapper;
import com.tzl.statistics.service.StatisticsDailyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.Schedules;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author tzl
 * @since 2022-06-08
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {

    @Autowired
    private UcenterClient ucenterClient;

    @Autowired
    private CourseClient courseClient;

    @Override
    public void countRegister(String day) {
        Result result = ucenterClient.countRegister(day);
        Integer registerNum = Integer.parseInt(result.getData().get("registerNum").toString());
        Integer loginNum = Integer.parseInt(result.getData().get("loginNum").toString());

        Result courseCount = courseClient.getCourseCount(day);
        Integer buyCount = Integer.parseInt(courseCount.getData().get("buyCount").toString());
        Integer viewCount = Integer.parseInt(courseCount.getData().get("viewCount").toString());
        Integer courseNum = Integer.parseInt(courseCount.getData().get("courseNum").toString());

        Result palyCount = courseClient.palyCount(day);
        Integer videoViewNum = Integer.parseInt(palyCount.getData().get("palyCount").toString());

        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.eq("date_calculated",day);
        StatisticsDaily daily = this.getOne(wrapper);
        if (daily == null){
            daily = new StatisticsDaily();
            daily.setDateCalculated(day)
                    .setRegisterNum(registerNum)
                    .setLoginNum(loginNum)
                    .setVideoViewNum(videoViewNum)
                    .setBuyCount(buyCount)
                    .setViewCount(viewCount)
                    .setCourseNum(courseNum);
            this.save(daily);
        }else {
            daily.setDateCalculated(day)
                    .setRegisterNum(registerNum)
                    .setLoginNum(loginNum)
                    .setVideoViewNum(videoViewNum)
                    .setBuyCount(buyCount)
                    .setViewCount(viewCount)
                    .setCourseNum(courseNum);
            this.updateById(daily);
        }

    }

    @Override
    public Map<String, Object> showData(String type, String startTime, String endTime) {
        Map<String, Object> map = new HashMap<>();

        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(type)){
            wrapper.select("date_calculated",type+" as num");
        }
        if (!StringUtils.isEmpty(startTime)){
            wrapper.ge("date_calculated",startTime);
        }
        if (!StringUtils.isEmpty(endTime)){
            wrapper.le("date_calculated",endTime);
        }
        List<StatisticsDaily> list = this.list(wrapper);


        List<Integer> numList = list.stream().map(StatisticsDaily::getNum).collect(Collectors.toList());
        List<String> dateList = list.stream().map(StatisticsDaily::getDateCalculated).collect(Collectors.toList());

        map.put("numList",numList);
        map.put("dateList",dateList);
        return map;
    }
}
