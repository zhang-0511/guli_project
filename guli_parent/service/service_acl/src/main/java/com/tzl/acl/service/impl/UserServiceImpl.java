package com.tzl.acl.service.impl;

import com.tzl.model.entity.User;
import com.tzl.acl.mapper.UserMapper;
import com.tzl.acl.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author tzl
 * @since 2022-06-12
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
