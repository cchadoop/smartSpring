package com.smart.config;

import com.smart.auth.UserAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

//@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true) //开启权限注解,默认是关闭的
public class SecurityConfig2 extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserAuthenticationProvider userAuthenticationProvider;

    @Bean
    public UserDetailsService userDetailsService(){
        // ensure the passwords are encoded properly
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withDefaultPasswordEncoder().username("user").password("password").roles("USER").build());
        return manager;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /*http.authorizeRequests().antMatchers("/vip1").hasRole("ROLE_VIP1")
                        .antMatchers("/vip2").hasRole("ROLE_VIP2");*/
        super.configure(http);

        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/level1/**").hasRole("vip1")
                .antMatchers("/level2/**").hasRole("vip2")
                .antMatchers("/level3/**").hasRole("vip3");

        http.formLogin();   //默认表单登录
        http.rememberMe();  //记住我

        http.authorizeRequests().antMatchers(HttpMethod.GET,"/",
                "/*.html",
                "/favicon.ico",
                "/**/*.html",
                "/**/*.css",
                "/**/*.js",
                "/swagger-resources/**",
                "/v2/api-docs/**")
                .permitAll()
                .antMatchers("/**/login", "/**/register", "/getCode", "/**/emailCode", "/outLogin",
                "/**/app", "/**/outLogin", "/businessConfig/**", "/businessAnnouncement/**", "/sync/**", "/reportMonthlyBase/**",
                "/fileRecord/**","/druid/**")// 对登录注册要允许匿名访问
                .permitAll()
                .antMatchers(HttpMethod.OPTIONS)//跨域请求会先进行一次options请求
                .permitAll()
                .anyRequest()// 除上面外的所有请求全部需要鉴权认证
                .permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //这里可启用我们自己的登陆验证逻辑
        auth.authenticationProvider(userAuthenticationProvider);
    }
}
