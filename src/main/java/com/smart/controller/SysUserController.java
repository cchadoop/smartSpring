package com.smart.controller;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.smart.contant.enums.DeFlagEnum;
import com.smart.entity.SysUser;
import com.smart.service.SysUserService;
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
@RequestMapping("/sys-user")
public class SysUserController {
    @Autowired
    private  SysUserService sysUserService;

    @GetMapping("/list")
    public List<SysUser> list(SysUser entity){
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.isNull(DeFlagEnum.DELETE.getName());
        wrapper.setEntity(entity);
        return sysUserService.list(wrapper);
    }

    @GetMapping("/page")
    public IPage<SysUser> page(@RequestParam(value = "current", defaultValue = "1") int current,
                               @RequestParam(value = "size", defaultValue = "10") int size,
                               SysUser entity) {
        IPage<SysUser> page = new Page<>(current, size);
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.isNull(DeFlagEnum.DELETE.getName());
        wrapper.setEntity(entity);
        return sysUserService.page(page, wrapper);
    }

    @PostMapping("/save")
    public SysUser save(@RequestBody SysUser entity) {
        if (StrUtil.isEmpty(entity.getUserId())) {
            entity.setUserId(IdUtil.simpleUUID());
            entity.setCreateTime(LocalDateTime.now());
            sysUserService.save(entity);
        } else {
            entity.setUpdateTime(LocalDateTime.now());
            sysUserService.updateById(entity);
        }
        return entity;
    }

    @DeleteMapping("/remove")
    public boolean remove(@RequestParam("id") String id) {
        SysUser entity = sysUserService.getById(id);
        if (Objects.nonNull(entity)) {
            entity.setUpdateTime(LocalDateTime.now());
            entity.setDeFlag(DeFlagEnum.DELETE.getValue());
            return sysUserService.updateById(entity);
        }
        return false;
    }
}
