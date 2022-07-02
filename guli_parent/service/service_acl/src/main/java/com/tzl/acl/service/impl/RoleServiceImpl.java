package com.tzl.acl.service.impl;

import com.tzl.model.entity.Role;
import com.tzl.acl.mapper.RoleMapper;
import com.tzl.acl.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author tzl
 * @since 2022-06-12
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

}
