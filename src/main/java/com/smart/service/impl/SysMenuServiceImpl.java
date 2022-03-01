package com.smart.service.impl;

import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smart.contant.enums.DeFlagEnum;
import com.smart.entity.SysMenu;
import com.smart.entity.vo.MenuTreeResult;
import com.smart.mapper.SysMenuMapper;
import com.smart.service.SysMenuService;
import com.sun.istack.internal.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author smart
 * @since 2022-02-11
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Override
    public List<Tree<String>> treeMenu() {
        List<String> functionIds = Stream.of("2","201", "209", "20901", "20902","20903").collect(Collectors.toList());
        List<TreeNode<String>> collect = baseMapper
                .selectList(Wrappers.<SysMenu>lambdaQuery().in(SysMenu::getMenuId,functionIds).orderByAsc(SysMenu::getSortNum)).stream()
                .map(getNodeFunction())
                .collect(Collectors.toList());
        return TreeUtil.build(collect, "0");
        /*List<SysMenu> collect = baseMapper
                .selectList(Wrappers.<SysMenu>lambdaQuery().orderByAsc(SysMenu::getSortNum)).stream()
                .collect(Collectors.toList());
        TreeNodeConfig config = new TreeNodeConfig();
        config.setIdKey("menuId");
        config.setNameKey("menuName");
        config.setWeightKey("sortNum");
//        config.setDeep(3);
        return TreeUtil.build(collect, "0", config, (treeNode, tree) -> {
            tree.setId(treeNode.getMenuId());
            tree.setParentId(treeNode.getParentId());
            tree.setWeight(treeNode.getSortNum());
            tree.setName(treeNode.getMenuName());
            // 扩展属性 ...
            tree.putExtra("icon", treeNode.getIcon());
            tree.putExtra("path", treeNode.getUri());
            tree.putExtra("type", treeNode.getMenuType());
            tree.putExtra("permission", treeNode.getPermissionCode());
        });*/
    }

    @Override
    public List<Tree<String>> treeMenu(String parentId) {
        List<TreeNode<String>> collect = baseMapper
                .selectList(Wrappers.<SysMenu>lambdaQuery().orderByAsc(SysMenu::getSortNum)).stream()
                .map(getNodeFunction())
                .collect(Collectors.toList());
        return TreeUtil.build(collect, parentId);
    }

    @Override
    public List<MenuTreeResult> menuTree() {
        List<MenuTreeResult> list = new ArrayList<>();
        // parentid 父级菜单id设置为0
        convertTree(list, "0", new ArrayList<>());
        return list;
    }

    private void convertTree(List<MenuTreeResult> tree, String parentId, List<String> functionIds) {
        QueryWrapper<SysMenu> queryWrapper = new QueryWrapper<>();
        SysMenu sysMenu = new SysMenu();
        if (StrUtil.isNotEmpty(parentId)) {
            sysMenu.setParentId(parentId);
        } else {
            queryWrapper.isNull("PARENT_ID");
        }
        queryWrapper.isNull(DeFlagEnum.DELETE.getName());
        queryWrapper.in(!functionIds.isEmpty(), "MENU_ID", functionIds);
        queryWrapper.setEntity(sysMenu);
        List<SysMenu> list = this.list(queryWrapper);
        if (!CollectionUtils.isEmpty(list)) {
            list.forEach(m -> {
                MenuTreeResult node = new MenuTreeResult();
                node.setMenuId(m.getMenuId());
                node.setMenuName(m.getMenuName());
                node.setMenuType(m.getMenuType());
                node.setParentId(m.getParentId());
                node.setUri(m.getUri());
                node.setIcon(m.getIcon());
                node.setSortNum(m.getSortNum());
                node.setPermissionCode(m.getPermissionCode());
                node.setSecurityLevel(m.getSecurityLevel());
                //别名
                node.setName(m.getMenuName());
                node.setPath(m.getUri());
                Map<String,Object> map = new HashMap<>();
                map.put("title",m.getMenuName());
                map.put("icon",m.getIcon());
                node.setMeta(map);
                tree.add(node);
                node.setChildren(new ArrayList<>());
                convertTree(node.getChildren(), node.getMenuId(), functionIds);
            });
        }


    }


    @NotNull
    private Function<SysMenu, TreeNode<String>> getNodeFunction() {
        return menu -> {
            TreeNode<String> node = new TreeNode<>();
            node.setId(menu.getMenuId());
            node.setName(menu.getMenuName());
            node.setParentId(menu.getParentId());
//            node.setWeight(menu.getSortNum());
            // 扩展属性
            Map<String, Object> extra = new HashMap<>();
            extra.put("icon", menu.getIcon());
            extra.put("path", menu.getUri());
            extra.put("name", menu.getMenuName());
            extra.put("type", menu.getMenuType());
            extra.put("permission", menu.getPermissionCode());
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("title",menu.getMenuName());
            extra.put("meta",jsonObject);
            node.setExtra(extra);
            return node;
        };
    }
}
