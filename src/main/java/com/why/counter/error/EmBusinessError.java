package com.why.counter.error;

/**
 * @Author WHY
 * @Date 2021-01-20
 * @Version 1.0
 */
public enum EmBusinessError implements CommonError {
    //通用错误类型 10001
    PARAMETER_VALIDATION_ERROR(10001,"参数不合法"),

    //账号已存在
    PARAMETER_ACCOUNT_EXIST(10002, "用户名重复"),

    //未知错误
    UNKNOWN_ERROR(10003,"未知错误")
    ;

    private EmBusinessError(int errCode, String errMsg){
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    private int errCode;
    private String errMsg;


    @Override
    public int gerErrCode() {
        return this.errCode;
    }

    @Override
    public String getErrMsg() {
        return this.errMsg;
    }

    @Override
    public CommonError setErrMsg(String errMsg) {
        this.errMsg = errMsg;
        return this;
    }
}
