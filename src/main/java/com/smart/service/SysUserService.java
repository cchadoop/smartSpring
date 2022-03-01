package com.smart.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.smart.entity.SysRole;
import com.smart.entity.SysUser;
import com.smart.entity.UserInfo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author smart
 * @since 2022-02-11
 */
public interface SysUserService extends IService<SysUser> {

    UserInfo loadUserByUsername(String userName);

    List<SysRole> selectSysRoleByUserId(String userId);
}
