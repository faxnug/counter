package com.why.counter.util;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @Author WHY
 * @Date 2021-01-08
 * @Version 1.0
 */
@Component
public class DbUtil {
    private static DbUtil dbUtil = null;

    private DbUtil(){

    }

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    private void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate){
        this.sqlSessionTemplate = sqlSessionTemplate;
    }

    private SqlSessionTemplate getSqlSessionTemplate(){
        return this.sqlSessionTemplate;
    }

    @PostConstruct
    private void init(){
        dbUtil = new DbUtil();
        dbUtil.setSqlSessionTemplate(this.sqlSessionTemplate);
    }

    public static long getId(){
        Long res = dbUtil.getSqlSessionTemplate().selectOne("testMapper.queryBalance");
        if(res == null){
            return -1;
        }else{
            return res;
        }
    }

}