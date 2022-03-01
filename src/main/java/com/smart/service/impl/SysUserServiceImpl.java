package com.smart.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smart.entity.SysRole;
import com.smart.entity.SysUser;
import com.smart.entity.UserInfo;
import com.smart.mapper.SysUserMapper;
import com.smart.service.SysUserService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author smart
 * @since 2022-02-11
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Override
    public UserInfo loadUserByUsername(String userName) {
        UserInfo userInfo = new UserInfo();
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("USERNAME",userName);
        SysUser sysUser = baseMapper.selectOne(queryWrapper);
        userInfo.setSysUser(sysUser);
        return userInfo;
    }

    @Override
    public List<SysRole> selectSysRoleByUserId(String userId) {
        return null;
    }
}
