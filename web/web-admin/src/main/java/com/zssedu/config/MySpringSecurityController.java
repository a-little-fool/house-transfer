package com.zssedu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author 周书胜
 * @date 2023年03月11 9:40
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MySpringSecurityController extends WebSecurityConfigurerAdapter {
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("root")
//                .password(new BCryptPasswordEncoder().encode("wyqhz581"))
//                .roles("");
//
//    }

    /**
     * 必须指定加密方式，上下加密方式要一致
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //允许iframe嵌套显示
        http.headers().frameOptions().disable();

        //登录设置
        http
                .authorizeRequests()
                .antMatchers("/static/**","/login").permitAll()  //允许匿名用户访问的路径
                .anyRequest().authenticated()    // 其它页面全部需要验证
                .and()
                .formLogin()
                .loginPage("/login")    //用户未登录时，访问任何需要权限的资源都转跳到该路径，即登录页面，此时登陆成功后会继续跳转到第一次访问的资源页面（相当于被过滤了一下）
                .defaultSuccessUrl("/") //登录认证成功后默认转跳的路径，意思时admin登录后也跳转到/user
                .and()
                .logout()
                .logoutUrl("/logout")   //退出登陆的路径，指定spring security拦截的注销url,退出功能是security提供的
                .logoutSuccessUrl("/login")//用户退出后要被重定向的url
    		    .and()
                .csrf().disable()//关闭跨域请求伪造功能【这个很重要，防止钓鱼网站】
                .exceptionHandling().accessDeniedHandler(new CustomAccessDeniedHandler());
    }
}
