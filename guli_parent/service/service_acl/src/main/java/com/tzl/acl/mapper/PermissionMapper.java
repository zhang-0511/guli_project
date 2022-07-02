package com.tzl.acl.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tzl.model.entity.Permission;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 权限 Mapper 接口
 * </p>
 *
 * @author tzl
 * @since 2022-06-12
 */
@Mapper
public interface PermissionMapper extends BaseMapper<Permission> {

}
