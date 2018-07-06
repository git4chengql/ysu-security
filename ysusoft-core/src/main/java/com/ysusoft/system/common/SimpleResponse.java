package com.ysusoft.system.common;

/**
 * @author chengql
 * @create 2018-06-26 下午5:35
 **/
public class SimpleResponse {
    private Object content;

    public SimpleResponse(Object content){
        this.content = content;
    }
    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }
}
