package com.smart.controller;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.smart.contant.enums.DeFlagEnum;
import com.smart.entity.SysRole;
import com.smart.service.SysRoleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author smart
 * @since 2022-02-11
 */
@RestController
@RequestMapping("/sys-role")
public class SysRoleController {
    @Autowired
    private  SysRoleService sysRoleService;

    @GetMapping("/list")
    public List<SysRole> list(SysRole entity){
        QueryWrapper<SysRole> wrapper = new QueryWrapper<>();
        wrapper.isNull(DeFlagEnum.DELETE.getName());
        wrapper.setEntity(entity);
        return sysRoleService.list(wrapper);
    }

    @GetMapping("/page")
    public IPage<SysRole> page(@RequestParam(value = "current", defaultValue = "1") int current,
                               @RequestParam(value = "size", defaultValue = "10") int size,
                               SysRole entity) {
        IPage<SysRole> page = new Page<>(current, size);
        QueryWrapper<SysRole> wrapper = new QueryWrapper<>();
        wrapper.isNull(DeFlagEnum.DELETE.getName());
        wrapper.setEntity(entity);
        return sysRoleService.page(page, wrapper);
    }

    @PostMapping("/save")
    public SysRole save(@RequestBody SysRole entity) {
        if (StrUtil.isEmpty(entity.getRoleId())) {
            entity.setRoleId(IdUtil.simpleUUID());
            entity.setCreateTime(LocalDateTime.now());
            sysRoleService.save(entity);
        } else {
            entity.setUpdateTime(LocalDateTime.now());
            sysRoleService.updateById(entity);
        }
        return entity;
    }

    @DeleteMapping("/remove")
    public boolean remove(@RequestParam("id") String id) {
        SysRole entity = sysRoleService.getById(id);
        if (Objects.nonNull(entity)) {
            entity.setUpdateTime(LocalDateTime.now());
            entity.setDeFlag(DeFlagEnum.DELETE.getValue());
            return sysRoleService.updateById(entity);
        }
        return false;
    }
}
