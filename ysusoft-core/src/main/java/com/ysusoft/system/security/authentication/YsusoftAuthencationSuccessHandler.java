package com.ysusoft.system.security.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ysusoft.system.properties.LoginType;
import com.ysusoft.system.properties.SystemProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author chengql
 * @create 2018-01-03 下午10:16
 * 认证成功处理器
 **/
@Component
public class YsusoftAuthencationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {//implements AuthenticationSuccessHandler {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SystemProperties systemProperties;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        logger.info(systemProperties.getI18N().getLogin_success());
        logger.info("登录方式为:"+systemProperties.getLoginType());
        //根据用户配置实现不同方式的跳转
        if(LoginType.JSON.equals(systemProperties.getLoginType())){
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(authentication));
        }else{
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(authentication));
            super.onAuthenticationSuccess(request,response,authentication);
        }

    }
}
