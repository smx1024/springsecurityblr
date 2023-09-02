package com.sx.demo2.config;

import com.sx.demo2.filter.KaptchaFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;

import javax.sql.DataSource;
import java.util.UUID;

@Configuration
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {
    private  final UserDetailsService userDetailsService;

    @Autowired
    public WebSecurityConfigurer(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }
    @Bean
    public KaptchaFilter kaptchaFilter() throws Exception {
        KaptchaFilter kaptchaFilter = new KaptchaFilter();
        //指定接收验证码请求参数名
        kaptchaFilter.setKaptcha("kaptcha");
        //指定认证管理器
        kaptchaFilter.setAuthenticationManager(authenticationManagerBean());
        //指定认证成功和失败处理
        kaptchaFilter.setAuthenticationSuccessHandler(new MyAuthenticationSuccessHandler());
//        kaptchaFilter.setAuthenticationFailureHandler(new MyAuthenticationFailureHandler());
        //指定处理登录
        kaptchaFilter.setFilterProcessesUrl("/doLogin");
        kaptchaFilter.setUsernameParameter("uname");
        kaptchaFilter.setPasswordParameter("pwd");
        //成功时跳转
        kaptchaFilter.setAuthenticationSuccessHandler(((request, response, authentication) -> response.sendRedirect("/index")));
        kaptchaFilter.setAuthenticationFailureHandler(((request, response, authentication) -> response.sendRedirect("/login.html")));

        return kaptchaFilter;
    }
    @Autowired
    private DataSource dataSource;
    @Bean
    public PersistentTokenRepository persistentTokenRepository(){
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setCreateTableOnStartup(false);//只需要没有表时设置为 true
        jdbcTokenRepository.setDataSource(dataSource);
        return jdbcTokenRepository;
    }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .mvcMatchers("/index","vc.jpg").permitAll() //放行/index请求
                .mvcMatchers("/login.html").permitAll() //放行/login.html请求
                .anyRequest().authenticated() //其它请求需要登录认证后才能访问
                .and()
                .formLogin() //默认form表单页面登录
                .loginPage("/login.html") //使用自定义登录页面登录页面登录
//                .loginProcessingUrl("/doLogin") //使用自定义登录页面时需要重新指定url，对应login.html中的action路径
//                .usernameParameter("uname") //重新指定用户名名称
//                .passwordParameter("pwd") //重新指定密码名称
//                .successForwardUrl("/index") //认证成功后跳转路径
                //forward 跳转路径  始终在认证成功之后跳转到指定请求 地址栏不变
                //.defaultSuccessUrl("/hello") //默认认证成功后跳转路径
                //.defaultSuccessUrl("/hello",true) //第二个参数设置为true时总是跳转，效果同successForwardUrl一致，默认false
                //redirect 重定向  注意:如果之前有请求过的路径,会优先跳转之前的请求路径 地址栏改变
//                .failureUrl("/login.html") //登录失败后跳转路径
//               .and()
//                .logout()
//                .logoutSuccessHandler(new MyLogoutSuccessHandler())   //前端后端分离注销成功后返回   登录成功失败一样
                .and()
                .rememberMe().tokenRepository(persistentTokenRepository()).userDetailsService(userDetailsService)
                .and().csrf()//开启csrf防御
                .and()
                .sessionManagement()  //开启会话管理
                .maximumSessions(1)  //允许同一个用户只允许创建一个会话
                .expiredUrl("/login")//会话过期处理

        ;//此处先关闭CSRF跨站保护
        http.addFilterAt(kaptchaFilter(), UsernamePasswordAuthenticationFilter.class);
    }

}
