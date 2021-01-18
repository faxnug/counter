package com.why.counter.bean.res;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author WHY
 * @Date 2021-01-14
 * @Version 1.0
 */
@AllArgsConstructor
public class CounterRes {

    @Getter
    private int code;

    @Getter
    private String message;

    @Getter
    private Object data;

    public CounterRes(Object data){
        this(0,"",data);
    }
}
