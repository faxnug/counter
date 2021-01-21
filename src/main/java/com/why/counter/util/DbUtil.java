package com.why.counter.util;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultimap;
import com.why.counter.bean.res.Account;
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

    public static Account queryAccount(long uid, String password){
        return dbUtil.getSqlSessionTemplate().selectOne(
                "userMapper.queryAccount",
                ImmutableMap.of("UId",uid,"Password",password));
    }

    public static void updateLoginTime(long uid, String nowDate, String nowTime){
        dbUtil.getSqlSessionTemplate().update(
                "userMapper.updateAccountLoginTime",
                ImmutableMap.of("UId", uid,"ModifyDate", nowDate,"ModifyTime", nowTime));
    }

    public static int updatePwd(long uid, String oldPwd, String newPwd){
        return dbUtil.getSqlSessionTemplate().update(
                "userMapper.updatePwd",
                ImmutableMap.of("UId", uid, "OldPwd", oldPwd,"NewPwd", newPwd));
    }

    public static int insertAccount(long uid, String password, String nowDate, String nowTime){
        return dbUtil.getSqlSessionTemplate().insert(
                "userMapper.insertAccount",
                ImmutableMap.of("UId", uid, "Password", password,"CreateDate",nowDate,"CreateTime",nowTime));
    }

    public static int queryUid(long uid){
        return dbUtil.getSqlSessionTemplate().selectOne("userMapper.queryUID", ImmutableMap.of("UId", uid));
    }

}
