package com.tzl.edu.controller;


import com.tzl.model.entity.Chapter;
import com.tzl.model.vo.ChapterVo;
import com.tzl.edu.service.ChapterService;
import com.tzl.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author tzl
 * @since 2022-05-13
 */
@Slf4j
@Api(tags = "课程小节接口类")
@RestController
@RequestMapping("/edu/chapter")
public class ChapterController {

    @Autowired
    private ChapterService chapterService;

    @GetMapping("/getChapterVideo/{courseId}")
    @ApiOperation("获取课程章节小节接口")
    public Result getChapterVideo(@ApiParam(name = "courseId", value = "课程信息Id", required = true)@PathVariable("courseId") String courseId){
        List<ChapterVo> list = chapterService.getChapterVideoByCourseId(courseId);
        return Result.ok().data("list",list);
    }

    @PostMapping("/saveChapter")
    @ApiOperation("添加课程章节信息接口")
    public Result saveChapter(@ApiParam(name = "chapter", value = "课程章节信息", required = true)@RequestBody Chapter chapter){
        boolean b = chapterService.save(chapter);
        if (b) {
            return Result.ok();
        }else {
            return Result.error();
        }
    }

    @GetMapping("/getChapterInfo/{chapterId}")
    @ApiOperation("根据课程章节ID获取章节信息接口")
    public Result getChapterInfo(@ApiParam(name = "chapterId", value = "课程章节信息Id", required = true)@PathVariable("chapterId") String chapterId){
        Chapter chapter = chapterService.getById(chapterId);
        return Result.ok().data("chapter",chapter);
    }
    @PutMapping("/updateChapter")
    @ApiOperation("根据课程章节ID修改章节信息接口")
    public Result updateChapter(@ApiParam(name = "chapter", value = "课程章节信息", required = true)@RequestBody Chapter chapter){
        boolean b = chapterService.updateById(chapter);
        if (b) {
            return Result.ok();
        }else {
            return Result.error();
        }
    }

    @DeleteMapping("/deleteChapter/{chapterId}")
    @ApiOperation("根据课程章节ID删除章节信息接口")
    public Result deleteChapter(@ApiParam(name = "chapterId", value = "课程章节信息Id", required = true)@PathVariable("chapterId") String chapterId){
        boolean b = chapterService.removeChapterById(chapterId);
        if (b) {
            return Result.ok();
        }else {
            return Result.error();
        }
    }
}

