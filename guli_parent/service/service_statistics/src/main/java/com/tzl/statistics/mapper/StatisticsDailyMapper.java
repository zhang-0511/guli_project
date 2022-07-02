package com.tzl.statistics.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tzl.model.entity.StatisticsDaily;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 网站统计日数据 Mapper 接口
 * </p>
 *
 * @author tzl
 * @since 2022-06-08
 */
@Mapper
public interface StatisticsDailyMapper extends BaseMapper<StatisticsDaily> {

}
