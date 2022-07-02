package com.tzl.edu.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tzl.client.UcenterClient;
import com.tzl.edu.service.CommentService;
import com.tzl.model.entity.Comment;
import com.tzl.model.entity.UcenterMember;
import com.tzl.result.Result;
import com.tzl.utils.JwtHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 * 评论 前端控制器
 * </p>
 *
 * @author tzl
 * @since 2022-05-13
 */
@Slf4j
@Api(tags = "课程评论接口")
@RestController
@RequestMapping("/edu/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;
    @Autowired
    private UcenterClient ucenterClient;

    @GetMapping("/getCommentByid/{page}/{limit}")
    @ApiOperation("根据课程id获取课程评论")
    public Result getCommentByid(
            @ApiParam(name = "page",value = "当前页码",required = true) @PathVariable("page")Long page,
            @ApiParam(name = "limit",value = "每页记录数",required = true) @PathVariable("limit")Long limit,
            @ApiParam(name = "courseId",value = "课程id",required = false) String courseId
    ){
        Page<Comment> pageParam = new Page<>(page, limit);
        Map<String, Object> map = commentService.getCommentByid(pageParam,courseId);
        return Result.ok().data(map);
    }
    @ApiOperation(value = "添加评论")
    @PostMapping("/saveComment")
    public Result saveComment(@ApiParam(name = "comment",value = "评论内容",required = true)@RequestBody Comment comment, HttpServletRequest request) {
        String token = request.getHeader("token");
        String userId = JwtHelper.getUserId(token);
        if(StringUtils.isEmpty(userId)) {
            return Result.error().code(224).message("请登录");
        }
        comment.setMemberId(userId);
        Object ucenterMember = ucenterClient.getUserInfo(userId).getData().get("ucenterMember");
        String json = JSONObject.toJSONString(ucenterMember);
        UcenterMember user = JSONObject.parseObject(json, UcenterMember.class);
        comment.setNickname(user.getNickname());
        comment.setAvatar(user.getAvatar());
        commentService.save(comment);
        return  Result.ok();
    }

    @DeleteMapping("/deleteById/{id}")
    @ApiOperation("删除自身评论")
    public Result deleteById(@ApiParam(name = "id",value = "评论id",required = true) @PathVariable("id")String id, HttpServletRequest request){
        String token = request.getHeader("token");
        String userId = JwtHelper.getUserId(token);
        if(StringUtils.isEmpty(userId)) {
            return Result.error().code(224).message("请登录");
        }
        commentService.removeById(id);
        return Result.ok();
    }
}

