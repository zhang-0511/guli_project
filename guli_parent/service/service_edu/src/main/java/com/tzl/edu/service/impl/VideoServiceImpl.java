package com.tzl.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tzl.client.VodClient;
import com.tzl.model.entity.Video;
import com.tzl.model.vo.VideoInfoVo;
import com.tzl.edu.mapper.VideoMapper;
import com.tzl.edu.service.VideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tzl.result.Result;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author tzl
 * @since 2022-05-13
 */
@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements VideoService {

    @Autowired
    private VodClient vodClient;

    @Override
    public boolean saveVideoInfo(VideoInfoVo videoInfoVo) {
        Video video = new Video();
        BeanUtils.copyProperties(videoInfoVo,video);
        if (videoInfoVo.getFree()){
            video.setIsFree(1);
        }else {
            video.setIsFree(0);
        }
        int insert = this.baseMapper.insert(video);
        return insert > 0;
    }

    @Override
    public boolean updateVideoInfoById(VideoInfoVo videoInfoVo) {
        Video video = new Video();
        BeanUtils.copyProperties(videoInfoVo, video);
        if (videoInfoVo.getFree()) {
            video.setIsFree(1);
        }else {
            video.setIsFree(0);
        }
        int i = this.baseMapper.updateById(video);
        return i > 0;
    }

    @Override
    public VideoInfoVo getVideoInfoFormById(String videoId) {
        Video video = this.baseMapper.selectById(videoId);
        VideoInfoVo videoInfoVo = new VideoInfoVo();
        BeanUtils.copyProperties(video,videoInfoVo);
        videoInfoVo.setFree(video.getIsFree() == 1);
        return videoInfoVo;
    }

    //根据课程id 删除小节，删除对应视频
    @Override
    public boolean removeByCourseId(String courseId) {
        QueryWrapper<Video> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id", courseId);
        List<Video> videoList = baseMapper.selectList(queryWrapper);
        if (videoList != null && videoList.size() > 0){
            List<String> videoSourceIdList = videoList.stream().map(Video::getVideoSourceId).collect(Collectors.toList());
            if (videoSourceIdList != null && videoSourceIdList.size() > 0) {
                Result result = vodClient.deleteAliyunVideoBatch(videoSourceIdList);
                if (result.getCode() !=200){
                    return false;
                }
            }
        }
        int count = baseMapper.delete(queryWrapper);
        return count > 0;
    }

    @Override
    public void updatePlayCount(String videoSourceId) {
        this.baseMapper.updatePlayCount(videoSourceId);
    }

    @Override
    public Integer palyCount(String day) {
        Integer palyCount = this.baseMapper.palyCount(day);
        if (palyCount == null || palyCount < 0){
            palyCount = 0;
        }
        return palyCount;
    }
}
