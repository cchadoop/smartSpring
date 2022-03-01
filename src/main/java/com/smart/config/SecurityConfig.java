package com.smart.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


/**
 * SpringSecurity的配置
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Slf4j
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf()// 由于使用的是JWT，我们这里不需要csrf
                .disable()
                .sessionManagement()// 基于token，所以不需要session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, // 允许对于网站静态资源的无授权访问
                        "/",
                        "/*.html",
                        "/favicon.ico",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js",
                        "/swagger-resources/**",
                        "/v2/api-docs/**"
                )
                .permitAll()
                .antMatchers("/**/login", "/**/register", "/**")// 对登录注册要允许匿名访问
                .permitAll()
                .antMatchers(HttpMethod.OPTIONS)//跨域请求会先进行一次options请求
                .permitAll()
                .antMatchers("/**")//测试时全部运行访问
                .permitAll()
                .anyRequest()// 除上面外的所有请求全部需要鉴权认证
                .authenticated();
        // 禁用缓存
        httpSecurity.headers().cacheControl();
//        // 添加JWT filter
//        httpSecurity.addFilterBefore(jwtFilterConfig(), UsernamePasswordAuthenticationFilter.class);
        //添加自定义未授权和未登录结果返回
        httpSecurity.exceptionHandling()
                .accessDeniedHandler((request, response, e) -> {
                    response.setCharacterEncoding("UTF-8");
                    response.setContentType("application/json");
                    response.getWriter().println("未授权禁止访问");
                    response.getWriter().flush();
                })
                .authenticationEntryPoint((request, response, e) -> {
                    response.setCharacterEncoding("UTF-8");
                    response.setContentType("application/json");
                    response.getWriter().println("未登录禁止访问");
                    response.getWriter().flush();
                });
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //auth查询与加密
        auth.userDetailsService(userDetailsService())
                .passwordEncoder(passwordEncoder());
    }

    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * 获取用户信息类
     *
     * @return
     */
    @Override
    public UserDetailsService userDetailsService() {
        //获取并校验登录用户信息
        return username -> {
//            CurrentUser currentUser = loginService.getCurrentUser(username);
//            if (Objects.nonNull(currentUser)) {
//                return currentUser;
//            }
            throw new UsernameNotFoundException("用户名或密码错误");
        };
    }

    /**
     * 加密工具类
     *
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
