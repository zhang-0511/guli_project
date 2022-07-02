package com.tzl.edu.mapper;

import com.tzl.model.entity.Video;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 课程视频 Mapper 接口
 * </p>
 *
 * @author tzl
 * @since 2022-05-13
 */
public interface VideoMapper extends BaseMapper<Video> {

    public void updatePlayCount(String videoSourceId);

    public Integer palyCount(String day);
}
