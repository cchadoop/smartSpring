package com.smart.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class UserAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private SmartUserDetailService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 获取表单输入中返回的用户名
        String userName = (String) authentication.getPrincipal();
        // 获取表单中输入的密码
        String password = (String) authentication.getCredentials();

        UserDetails userInfo = userDetailsService.loadUserByUsername(userName);
        // 查看用户是否存在
        if(Objects.nonNull(userInfo)){
            throw new UsernameNotFoundException("用户名不存在");
        }
        // 验证密码是否正确
        // 还要判断密码是否正确，这里我们的密码使用BCryptPasswordEncoder进行加密的
        if (!new BCryptPasswordEncoder().matches(password, userInfo.getPassword())) {
            throw new BadCredentialsException("密码不正确");
        }


        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
