package com.tzl.acl.helper;


import com.tzl.acl.utils.CopyListUtils;
import com.tzl.model.entity.Permission;
import com.tzl.model.vo.PermissionVo;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 根据权限数据构建菜单数据
 * </p>
 *
 * @author qy
 * @since 2019-11-11
 */
public class PermissionHelper {

    /**
     * 使用递归方法建菜单
     * @param permissionList
     * @return
     */
    public static List<PermissionVo> bulid(List<Permission> permissionList) {
        List<PermissionVo> treeNodes = CopyListUtils.copyListProperties(permissionList, PermissionVo::new);
        List<PermissionVo> trees = new ArrayList<>();
        for (PermissionVo treeNode : treeNodes) {
            if ("0".equals(treeNode.getPid())) {
                treeNode.setLevel(1);
                trees.add(findChildren(treeNode,treeNodes));
            }
        }
        return trees;
    }

    /**
     * 递归查找子节点
     * @param treeNodes
     * @return
     */
    public static PermissionVo findChildren(PermissionVo treeNode,List<PermissionVo> treeNodes) {
        treeNode.setChildren(new ArrayList<PermissionVo>());

        for (PermissionVo it : treeNodes) {
            if(treeNode.getId().equals(it.getPid())) {
                int level = treeNode.getLevel() + 1;
                it.setLevel(level);
                if (treeNode.getChildren() == null) {
                    treeNode.setChildren(new ArrayList<>());
                }
                treeNode.getChildren().add(findChildren(it,treeNodes));
            }
        }
        return treeNode;
    }
}
