package com.ysusoft.system.security.exception;

import org.springframework.security.core.AuthenticationException;
/**
 * @author chengql
 * @create 2018-06-29 上午11:16
 **/
public class ImageCodeException extends AuthenticationException {
    public ImageCodeException(String msg) {
        super(msg);
    }
}
