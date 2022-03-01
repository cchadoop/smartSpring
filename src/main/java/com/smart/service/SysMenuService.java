package com.smart.service;

import cn.hutool.core.lang.tree.Tree;
import com.smart.entity.SysMenu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.smart.entity.vo.MenuTreeResult;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author smart
 * @since 2022-02-11
 */
public interface SysMenuService extends IService<SysMenu> {

    /**
     * 全部菜单树
     * @return
     */
    List<Tree<String>> treeMenu();


    /**
     *
     * @param parentId
     * @return
     */
    List<Tree<String>> treeMenu(String parentId);


    /**
     * 用户权限菜单
     */

    List<MenuTreeResult> menuTree();



}
