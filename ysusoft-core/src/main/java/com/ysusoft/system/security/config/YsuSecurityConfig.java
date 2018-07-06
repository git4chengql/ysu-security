package com.ysusoft.system.security.config;

import com.ysusoft.system.properties.SystemProperties;
import com.ysusoft.system.security.filter.ImageCodeFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

/**
 * @author chengql
 * @create 2018-01-02 下午5:31
 **/
@Configuration
public class YsuSecurityConfig extends WebSecurityConfigurerAdapter {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SystemProperties systemProperties;

    /**
     * 记住我功能中存储用户Token使用
     */
    @Autowired
    private DataSource dataSource;

    /**
     * 此对象的目的是记住我功能中使用Token查询用户信息
     */
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationSuccessHandler ysusoftAuthencationSuccessHandler;

    @Autowired
    private AuthenticationFailureHandler ysusoftAuthencationFailureHandler;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    /**
     * 记住我 存储 Token用bean
     * @return
     */
    @Bean
    public PersistentTokenRepository persistentTokenRepository(){
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        //jdbcTokenRepository.setCreateTableOnStartup(true);
        return jdbcTokenRepository;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        ImageCodeFilter filter = new ImageCodeFilter();

        filter.setAuthenticationFailureHandler(ysusoftAuthencationFailureHandler);
        //表单登录
        //增加自定义过滤器
        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
                 .formLogin()
                  //自定义登录页面,此地址可根据终端的不同自定义跳转逻辑
                 .loginPage(systemProperties.getLoginPage())
                  //定义登录处理地址，如是表单验证方式，则由实现UserDetailService的类来响应
                 .loginProcessingUrl(systemProperties.getLoginURL())
                  //登录成功处理
                 .successHandler(ysusoftAuthencationSuccessHandler)
                  //登录失败处理
                 .failureHandler(ysusoftAuthencationFailureHandler)
                 .and()
                     .rememberMe()
                     .tokenRepository(persistentTokenRepository())
                     .tokenValiditySeconds(3600)
                     .userDetailsService(userDetailsService)
                     .and()
                 .authorizeRequests()
                 .antMatchers(systemProperties.getLoginPage(),"/system/verifycode.html","/login.html","/getsmscode")
                 .permitAll()
                 .anyRequest()
                 .authenticated()
                 .and()
                 .csrf()
                 .disable();
    }
}
