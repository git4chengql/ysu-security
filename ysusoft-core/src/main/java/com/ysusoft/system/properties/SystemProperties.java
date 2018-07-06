package com.ysusoft.system.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author chengql
 * @create 2018-01-03 下午9:44
 * 系统配置属性读取
 **/
@Component
@ConfigurationProperties(prefix="ysusoft")
public class SystemProperties {

    private String loginPage = "/static/defaultLogin.html";
    private String loginURL = "/login.do";
    private I18N i18N;
    private LoginType loginType = LoginType.JSON;
    private ImageCodeProperties imageCode;

    public String getLoginPage() {
        return loginPage;
    }

    public void setLoginPage(String loginPage) {
        this.loginPage = loginPage;
    }

    public String getLoginURL() {
        return loginURL;
    }

    public void setLoginURL(String loginURL) {
        this.loginURL = loginURL;
    }

    public I18N getI18N() {
        return i18N;
    }

    public void setI18N(I18N i18N) {
        this.i18N = i18N;
    }

    public LoginType getLoginType() {
        return loginType;
    }

    public void setLoginType(LoginType loginType) {
        this.loginType = loginType;
    }

    public ImageCodeProperties getImageCode() {
        return imageCode;
    }

    public void setImageCode(ImageCodeProperties imageCode) {
        this.imageCode = imageCode;
    }
}
