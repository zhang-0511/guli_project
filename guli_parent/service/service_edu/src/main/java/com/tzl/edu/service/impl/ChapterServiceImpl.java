package com.tzl.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tzl.model.entity.Chapter;
import com.tzl.model.entity.Video;
import com.tzl.model.vo.ChapterVo;
import com.tzl.model.vo.VideoVo;
import com.tzl.edu.mapper.ChapterMapper;
import com.tzl.edu.service.ChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tzl.edu.service.VideoService;
import com.tzl.exceptionHander.GuliException;
import com.tzl.result.ResultCodeEnum;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author tzl
 * @since 2022-05-13
 */
@Service
public class ChapterServiceImpl extends ServiceImpl<ChapterMapper, Chapter> implements ChapterService {

    @Autowired
    private VideoService videoService;

    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {
        List<ChapterVo> list = new ArrayList<>();

        //1、查询课程章节信息
        QueryWrapper<Chapter> wrapperChapter = new QueryWrapper<>();
        wrapperChapter.eq("course_id",courseId).orderByAsc("sort");
        List<Chapter> chapterList = this.baseMapper.selectList(wrapperChapter);

        //查询章节的小节信息
        QueryWrapper<Video> wrapperVideo = new QueryWrapper<>();
        wrapperVideo.eq("course_id",courseId).orderByAsc("sort");
        List<Video> videoList = videoService.list(wrapperVideo);

        //分装数据
        if (chapterList != null && chapterList.size() > 0) {
            chapterList.forEach(c->{
                ChapterVo chapterVo = new ChapterVo();
                BeanUtils.copyProperties(c,chapterVo);

                List<VideoVo> videoVoList = new ArrayList<>();
                if (videoList != null && videoList.size() > 0) {
                    videoList.forEach(v->{
                        if (c.getId().equalsIgnoreCase(v.getChapterId())){
                            VideoVo videoVo = new VideoVo();
                            BeanUtils.copyProperties(v,videoVo);
                            videoVoList.add(videoVo);
                        }
                    });
                }
                chapterVo.setChildren(videoVoList);
                list.add(chapterVo);
            });
        }


        return list;
    }

    @Override
    public boolean removeChapterById(String chapterId) {
        QueryWrapper<Video> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id",chapterId);
        int count = videoService.count(wrapper);
        if (count > 0){
            throw new GuliException(ResultCodeEnum.FAIL);
        }
        int result = baseMapper.deleteById(chapterId);
        return result > 0;
    }

    @Override
    public boolean removeByCourseId(String courseId) {
        QueryWrapper<Chapter> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id", courseId);
        videoService.removeByCourseId(courseId);
        int count = baseMapper.delete(queryWrapper);
        return count > 0;
    }
}
