package com.why.counter.controller;

import com.why.counter.util.DbUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author WHY
 * @Date 2021-01-08
 * @Version 1.0
 */
@RestController
public class Hello {

//    @Autowired
//    private DbUtil dbUtil;

    @GetMapping("/hello")
    public String hello(){
       return "" + DbUtil.getId();
    }
}
