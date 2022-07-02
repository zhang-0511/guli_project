package com.tzl.edu.mapper;

import com.tzl.model.entity.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 评论 Mapper 接口
 * </p>
 *
 * @author tzl
 * @since 2022-05-13
 */
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {

}
