package com.smart.controller;

import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smart.contant.enums.DeFlagEnum;
import com.smart.entity.SysMenu;
import com.smart.entity.vo.MenuTreeResult;
import com.smart.service.SysMenuService;
import com.smart.util.ExcelUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author smart
 * @since 2022-02-10
 */
@Api(value = "菜单信息接口", tags = "菜单信息接口")
@RestController
@RequestMapping("/sys-menu")
public class SysMenuController {
    @Autowired
    private  SysMenuService sysMenuService;

    @ApiOperation(value = "单个查询",tags = "单个查询")
    @GetMapping("/list")
    public List<SysMenu> list(SysMenu entity){
        QueryWrapper<SysMenu> wrapper = new QueryWrapper<>();
        wrapper.isNull(DeFlagEnum.DELETE.getName());
        wrapper.setEntity(entity);
        return sysMenuService.list(wrapper);
    }

    @ApiOperation(value = "分页查询",tags = "分页查询")
    @GetMapping("/page")
    public IPage<SysMenu> page(@RequestParam(value = "current", defaultValue = "1") int current,
                               @RequestParam(value = "size", defaultValue = "10") int size,
                               SysMenu entity) {
        IPage<SysMenu> page = new Page<>(current, size);
        QueryWrapper<SysMenu> wrapper = new QueryWrapper<>();
        wrapper.isNull(DeFlagEnum.DELETE.getName());
        wrapper.setEntity(entity);
        return sysMenuService.page(page, wrapper);
    }

    @ApiOperation(value = "保存数据",tags = "保存数据")
    @PostMapping("/save")
    public SysMenu save(@RequestBody SysMenu entity) {
        if (StrUtil.isEmpty(entity.getMenuId())) {
            entity.setMenuId(IdUtil.simpleUUID());
            entity.setCreateTime(LocalDateTime.now());
            sysMenuService.save(entity);
        } else {
            entity.setUpdateTime(LocalDateTime.now());
            sysMenuService.updateById(entity);
        }
        return entity;
    }

    @ApiOperation(value = "删除数据",tags = "删除数据")
    @DeleteMapping("/remove")
    public boolean remove(@RequestParam("id") String id) {
        SysMenu entity = sysMenuService.getById(id);
        if (Objects.nonNull(entity)) {
            entity.setUpdateTime(LocalDateTime.now());
            entity.setDeFlag(DeFlagEnum.DELETE.getValue());
            return sysMenuService.updateById(entity);
        }
        return false;
    }

    // 获取菜单树
    @ApiOperation(value = "获取菜单树",tags = "获取菜单树")
    @GetMapping("treeMenu")
    private List<Tree<String>> treeMenu(){
        return sysMenuService.treeMenu();
    }

    @ApiOperation(value = "根据id获取菜单树",tags = "根据id获取菜单树")
    @GetMapping("treeMenu/{id}")
    private List<Tree<String>> treeMenu(@PathVariable("id")String id){
        return sysMenuService.treeMenu(id);
    }

    @ApiOperation(value = "构建递归获取菜单树",tags = "构建递归获取菜单树")
    @GetMapping("menuTree")
    private List<MenuTreeResult> menuTree(){
        return sysMenuService.menuTree();
    }

    @GetMapping("exportExcel")
    @ApiOperation(value = "导出数据",tags = "导出数据")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response){
        List<SysMenu> list = sysMenuService.list(Wrappers.<SysMenu>lambdaQuery().isNull(SysMenu::getDeFlag));
        ExcelUtils.createExcel(request,response,SysMenu.class,list,"菜单.xls");
    }

}
