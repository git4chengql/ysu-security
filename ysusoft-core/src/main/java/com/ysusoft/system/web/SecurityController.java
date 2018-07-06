package com.ysusoft.system.web;

import com.ysusoft.system.common.SimpleResponse;
import com.ysusoft.system.properties.SystemProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author chengql
 * @create 2018-06-26 下午5:13
 **/
@RestController
public class SecurityController {
    private Logger logger = LoggerFactory.getLogger(SecurityController.class);

    @Autowired
    private SystemProperties systemProperties;

    /**
     * 缓存验证前访问的页面
     */
    private RequestCache requestCache = new HttpSessionRequestCache();

    /**
     * 跳转策略
     */
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    /**
     * 當需要身份验证时跳转到这里
     * @return
     */
    @GetMapping("/authentication/require")
    @ResponseStatus(code= HttpStatus.UNAUTHORIZED)
    public SimpleResponse requireAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException{
        SavedRequest savedRequest = requestCache.getRequest(request,response);
        if(savedRequest != null){
            String targetUrl = savedRequest.getRedirectUrl();
            logger.info("引发跳转的请求是："+targetUrl);
            //根据需求响应不同的结果，此处是根据请求的地址简单做demo
            if(StringUtils.endsWithIgnoreCase(targetUrl,".html")||1==1){
              redirectStrategy.sendRedirect(request,response,"/login.html");
            }
        }
        return new SimpleResponse("访问的服务需要身份认证，请引导用户到登录页");
    }
}
