package com.tzl.acl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tzl.acl.utils.CopyListUtils;
import com.tzl.model.entity.Permission;
import com.tzl.acl.mapper.PermissionMapper;
import com.tzl.acl.service.PermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tzl.model.vo.PermissionVo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 权限 服务实现类
 * </p>
 *
 * @author tzl
 * @since 2022-06-12
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

    @Override
    public List<PermissionVo> queryAllMenu() {
        List<Permission> list = this.list(null);
        return bulidPermission(list);
    }

    /*
        封装菜单集合
     */
    private List<PermissionVo> bulidPermission(List<Permission> list) {
        //创建list集合，用于数据最终封装
        List<PermissionVo> finalNode = new ArrayList<>();
        List<PermissionVo> treeNodes = CopyListUtils.copyListProperties(list, PermissionVo::new);
        treeNodes.forEach(permission -> {
            //得到顶层菜单，pid=0菜单
            if("0".equalsIgnoreCase(permission.getPid())){
                permission.setLevel(1);
                //根据顶层菜单，向里面进行查询子菜单，封装到finalNode
                finalNode.add(selectChildren(permission,treeNodes));
            }
        });
        return finalNode;
    }

    private PermissionVo selectChildren(PermissionVo permission, List<PermissionVo> treeNodes) {
        //1、因为向一级菜单里面放二级菜单，二级里面放三级，把对象初始化
        permission.setChildren(new ArrayList<>());
        //2、遍历所有菜单list集合，进行判断比较，比较id和pid的值是否相同
        treeNodes.forEach(permissionVo -> {
            //判断id和pid是否相同
            if (permission.getId().equalsIgnoreCase(permissionVo.getPid())){
                int level = permission.getLevel() + 1;
                permissionVo.setLevel(level);
                //如果children为空，进行初始化
                if (permission.getChildren() == null){
                    permission.setChildren(new ArrayList<>());
                }
                //把查询出来的子菜单放入到父菜单里面
                permission.getChildren().add(selectChildren(permissionVo,treeNodes));
            }
        });
        return permission;
    }
}
