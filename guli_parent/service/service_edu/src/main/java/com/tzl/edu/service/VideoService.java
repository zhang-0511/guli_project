package com.tzl.edu.service;

import com.tzl.model.entity.Video;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tzl.model.vo.VideoInfoVo;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author tzl
 * @since 2022-05-13
 */
public interface VideoService extends IService<Video> {

    public boolean updateVideoInfoById(VideoInfoVo videoInfoVo);

    public boolean saveVideoInfo(VideoInfoVo videoInfoVo);

    public VideoInfoVo getVideoInfoFormById(String videoId);

    public boolean removeByCourseId(String courseId);

    public void updatePlayCount(String videoSourceId);

    public Integer palyCount(String day);
}
