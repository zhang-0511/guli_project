package com.tzl.statistics.service;

import com.tzl.model.entity.StatisticsDaily;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author tzl
 * @since 2022-06-08
 */
public interface StatisticsDailyService extends IService<StatisticsDaily> {

    public void countRegister(String day);

    public Map<String, Object> showData(String type, String startTime, String endTime);
}
