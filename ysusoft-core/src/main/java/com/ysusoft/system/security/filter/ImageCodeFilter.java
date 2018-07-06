package com.ysusoft.system.security.filter;

import com.ysusoft.system.common.ImageCode;
import com.ysusoft.system.security.exception.ImageCodeException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;
import org.apache.commons.lang.StringUtils;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;

/**
 * @author chengql
 * @create 2018-06-29 上午11:12
 * 此过滤器在Security过滤器链的最顶层，可将业务处理在此过滤器中实现
 **/
public class ImageCodeFilter extends OncePerRequestFilter implements InitializingBean{

    private AuthenticationFailureHandler authenticationFailureHandler;

    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    public AuthenticationFailureHandler getAuthenticationFailureHandler() {
        return authenticationFailureHandler;
    }

    public void setAuthenticationFailureHandler(AuthenticationFailureHandler authenticationFailureHandler) {
        this.authenticationFailureHandler = authenticationFailureHandler;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        if(StringUtils.equals("/login.do",httpServletRequest.getRequestURI()) && StringUtils.equalsIgnoreCase(httpServletRequest.getMethod(),"post")){
             try{
                 validateCode(new ServletWebRequest(httpServletRequest));
             }catch (ImageCodeException exception){
                 authenticationFailureHandler.onAuthenticationFailure(httpServletRequest,httpServletResponse,exception);
                 return;
             }
         }

        filterChain.doFilter(httpServletRequest,httpServletResponse);

    }

    /**
     * 验证码检测
     * @param request
     */
    private void validateCode(ServletWebRequest request){
        //从Session中获取ImageCode对象
        ImageCode imageCodeInSession = (ImageCode)sessionStrategy.getAttribute(request,"SESSION_IMAGE_CODE");

        //获取请求中将用户输入的验证码
        String code = request.getRequest().getParameter("code");

        if(StringUtils.isBlank(code)){
            throw new ImageCodeException("验证码不能为空");
        }

        if(imageCodeInSession == null){
            throw new ImageCodeException("验证码不存在");
        }

//        if(imageCodeInSession.isExpried()){
//            sessionStrategy.removeAttribute(request,"SESSION_IMAGE_CODE");
//            throw new ImageCodeException("验证码已过期");
//        }

        if(!StringUtils.equals(code,imageCodeInSession.getCode())){
            throw new ImageCodeException("您输入的验证码不正确");
        }

        sessionStrategy.removeAttribute(request,"SESSION_IMAGE_CODE");
    }


}
