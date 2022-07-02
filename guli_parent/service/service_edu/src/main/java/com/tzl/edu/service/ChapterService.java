package com.tzl.edu.service;

import com.tzl.model.entity.Chapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tzl.model.vo.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author tzl
 * @since 2022-05-13
 */
public interface ChapterService extends IService<Chapter> {

    public List<ChapterVo> getChapterVideoByCourseId(String courseId);

    public boolean removeChapterById(String chapterId);

    public boolean removeByCourseId(String courseId);
}
