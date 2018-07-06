package com.ysusoft.system.security.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ysusoft.system.properties.LoginType;
import com.ysusoft.system.properties.SystemProperties;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.slf4j.Logger;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

/**
 * @author chengql
 * @create 2018-01-03 下午10:33
 * 认证失败处理器
 **/
@Component
public class YsusoftAuthencationFailureHandler extends SimpleUrlAuthenticationFailureHandler{// implements AuthenticationFailureHandler {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SystemProperties systemProperties;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        logger.info(systemProperties.getI18N().getLogin_failure());
        //根据用户配置实现不同方式的跳转
        if(LoginType.JSON.equals(systemProperties.getLoginType())){
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(objectMapper.writeValueAsString(exception.getMessage()));
        }else{
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(objectMapper.writeValueAsString(exception.getMessage()));
           // super.onAuthenticationFailure(request,response,exception);
        }

    }
}
