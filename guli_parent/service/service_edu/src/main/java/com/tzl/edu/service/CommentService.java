package com.tzl.edu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tzl.model.entity.Comment;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 评论 服务类
 * </p>
 *
 * @author tzl
 * @since 2022-05-13
 */
public interface CommentService extends IService<Comment> {

    public Map<String, Object> getCommentByid(Page<Comment> pageParam, String courseId);
}
