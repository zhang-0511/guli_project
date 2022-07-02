package com.tzl.acl.helper;

import com.alibaba.fastjson.JSONObject;
import com.tzl.acl.utils.CopyListUtils;
import com.tzl.model.entity.Permission;
import com.tzl.model.vo.PermissionVo;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 根据权限数据构建登录用户左侧菜单数据
 * </p>
 *
 * @author qy
 * @since 2019-11-11
 */
public class MemuHelper {

    /**
     * 构建菜单
     * @param permissionList
     * @return
     */
    public static List<JSONObject> bulid(List<Permission> permissionList) {
        List<PermissionVo> treeNodes = CopyListUtils.copyListProperties(permissionList, PermissionVo::new);
        List<JSONObject> meuns = new ArrayList<>();
        if(treeNodes.size() == 1) {
            PermissionVo topNode = treeNodes.get(0);
            //左侧一级菜单
            List<PermissionVo> oneMeunList = topNode.getChildren();
            for(PermissionVo one :oneMeunList) {
                JSONObject oneMeun = new JSONObject();
                oneMeun.put("path", one.getPath());
                oneMeun.put("component", one.getComponent());
                oneMeun.put("redirect", "noredirect");
                oneMeun.put("name", "name_"+one.getId());
                oneMeun.put("hidden", false);

                JSONObject oneMeta = new JSONObject();
                oneMeta.put("title", one.getName());
                oneMeta.put("icon", one.getIcon());
                oneMeun.put("meta", oneMeta);

                List<JSONObject> children = new ArrayList<>();
                List<PermissionVo> twoMeunList = one.getChildren();
                for(PermissionVo two :twoMeunList) {
                    JSONObject twoMeun = new JSONObject();
                    twoMeun.put("path", two.getPath());
                    twoMeun.put("component", two.getComponent());
                    twoMeun.put("name", "name_"+two.getId());
                    twoMeun.put("hidden", false);

                    JSONObject twoMeta = new JSONObject();
                    twoMeta.put("title", two.getName());
                    twoMeun.put("meta", twoMeta);

                    children.add(twoMeun);

                    List<PermissionVo> threeMeunList = two.getChildren();
                    for(PermissionVo three :threeMeunList) {
                        if(StringUtils.isEmpty(three.getPath())) continue;

                        JSONObject threeMeun = new JSONObject();
                        threeMeun.put("path", three.getPath());
                        threeMeun.put("component", three.getComponent());
                        threeMeun.put("name", "name_"+three.getId());
                        threeMeun.put("hidden", true);

                        JSONObject threeMeta = new JSONObject();
                        threeMeta.put("title", three.getName());
                        threeMeun.put("meta", threeMeta);

                        children.add(threeMeun);
                    }
                }
                oneMeun.put("children", children);
                meuns.add(oneMeun);
            }
        }
        return meuns;
    }
}
