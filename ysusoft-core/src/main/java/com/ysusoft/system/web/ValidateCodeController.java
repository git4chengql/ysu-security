package com.ysusoft.system.web;

import com.ysusoft.system.common.ImageCode;
import com.ysusoft.system.properties.SystemProperties;
import com.ysusoft.system.utils.ImageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author chengql
 * @create 2018-01-04 上午9:41
 * 默认处理类
 **/
@RestController
public class ValidateCodeController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SystemProperties systemProperties;

    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    private static final String SESSION_KEY  = "SESSION_IMAGE_CODE";

    private static final String SESSION_SMSCODE_KEY = "SESSION_SMS_CODE";

    /**
     * 获取图形验证码
     * @param request
     * @param response
     * @throws IOException
     */
    @GetMapping("/code/image")
    public void verifyImageCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ImageCode imageCode = new ImageUtils().createImageCode(6,
                120,
                30);
        sessionStrategy.setAttribute(new ServletWebRequest(request),SESSION_KEY,imageCode);

        ImageIO.write(imageCode.getImage(),"JPEG",response.getOutputStream());
    }

    /**
     * 获取短信验证码
     * @param request
     */
    @GetMapping("/code/sms")
    public void getSmsCode(HttpServletRequest request){
        logger.info("获得短信验证码");
        sessionStrategy.setAttribute(new ServletWebRequest(request),SESSION_SMSCODE_KEY,null);
    }

    /**
     * 获取用户详细信息
     * @param authentication
     * @return
     */
    @GetMapping("/me")
    public Object getCurrentUser(Authentication authentication){
        return authentication;//SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * 获得用户基本信息
     * @param userDetail
     * @return
     */
    @GetMapping("/simple-me")
    public Object getCurrentUserDetail(@AuthenticationPrincipal UserDetails userDetail){
        return userDetail;
    }
}
