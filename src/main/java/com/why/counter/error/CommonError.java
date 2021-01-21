package com.why.counter.error;

/**
 * @Author WHY
 * @Date 2021-01-20
 * @Version 1.0
 */
public interface CommonError {
    public int gerErrCode();
    public String getErrMsg();
    public CommonError setErrMsg(String errMsg);
}
