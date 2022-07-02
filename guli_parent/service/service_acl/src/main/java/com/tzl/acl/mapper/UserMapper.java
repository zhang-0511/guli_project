package com.tzl.acl.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tzl.model.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author tzl
 * @since 2022-06-12
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
