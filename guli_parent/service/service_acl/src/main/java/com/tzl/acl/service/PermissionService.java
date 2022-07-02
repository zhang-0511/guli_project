package com.tzl.acl.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.tzl.model.entity.Permission;
import com.tzl.model.vo.PermissionVo;

import java.util.List;

/**
 * <p>
 * 权限 服务类
 * </p>
 *
 * @author tzl
 * @since 2022-06-12
 */
public interface PermissionService extends IService<Permission> {

    public List<PermissionVo> queryAllMenu();
}
