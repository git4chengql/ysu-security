package com.ysusoft.system.common;

import java.util.List;

/**
 * @author chengql
 * @create 2018-01-04 上午9:55
 * 通用返回结果
 **/
public class ResultInfo<T> {
    private boolean isTrue;
    private String returnMsg;
    private List<T> datas;

    public boolean isTrue() {
        return isTrue;
    }

    public void setTrue(boolean aTrue) {
        isTrue = aTrue;
    }

    public String getReturnMsg() {
        return returnMsg;
    }

    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }

    public List<T> getDatas() {
        return datas;
    }

    public void setDatas(List<T> datas) {
        this.datas = datas;
    }
}
