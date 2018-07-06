package com.ysusoft.system.social.qq.api.impl;

import com.ysusoft.system.social.qq.api.QQ;
import com.ysusoft.system.social.qq.bean.QQUserInfo;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;

/**
 * @author chengql
 * @create 2018-06-07 下午4:26
 **/
public class QQImpl extends AbstractOAuth2ApiBinding implements QQ {

    private String appId;

    private String openId;

    private static final String URL_GET_OPENID = "https://graph.qq.com/oauth2.0/me?access_token={0}";

    private static final String URL_GET_USERINFO = "https://graph.qq.com/user/get_user_info?" +
                                                    "access_token={0}&" +
                                                    "oauth_consumer_key={1}&" +
                                                    "openid={2}";


    public QQImpl(String access_token,String appId){
        super(access_token, TokenStrategy.ACCESS_TOKEN_PARAMETER);

        this.appId = appId;

        String url = java.text.MessageFormat.format(URL_GET_OPENID,access_token);

        String result = getRestTemplate().getForObject(url,String.class);

        System.out.println("result="+result);

        //this.openId = StringU
    }
    @Override
    public QQUserInfo getUserInfo() {
        return null;
    }
}
