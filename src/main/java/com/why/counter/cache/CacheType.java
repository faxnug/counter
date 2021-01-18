package com.why.counter.cache;

/**
 * @Author WHY
 * @Date 2021-01-15
 * @Version 1.0
 */
public enum CacheType {
    CAPTCHA("captcha:"),    //验证码
    ACCOUNT("account:"),    //账号
    ORDER("order:"),        //交易
    TRADE("trade:"),        //
    POSI("posi:"),          //
    ;

    private String type;
    CacheType(String type){
        this.type = type;
    }

    public String type(){
        return this.type;
    }

}
