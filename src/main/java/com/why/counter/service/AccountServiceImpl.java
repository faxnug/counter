package com.why.counter.service;

import com.why.counter.bean.res.Account;
import com.why.counter.cache.CacheType;
import com.why.counter.cache.RedisStringCache;
import com.why.counter.thirdpart.uuid.WhyUuid;
import com.why.counter.util.DbUtil;
import com.why.counter.util.JsonUtil;
import com.why.counter.util.TimeformatUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @Author WHY
 * @Date 2021-01-19
 * @Version 1.0
 */
@Component
public class AccountServiceImpl implements AccountService {
    @Override
    @Transactional
    public String register(long uid, String password, String captcha, String captchaId) throws Exception {
        //1.校验账号
        int uidcount = DbUtil.queryUid(uid);
        if(uidcount > 0){
            //用户名重复
            return "用户名重复";
        }

        //2.入参的合法性验证,这里写的不严谨，只校验了字符串
        if(StringUtils.isAllBlank(password,captcha,captchaId)){
            return "密码/短信验证码不能为空";
        }

        //3.校验缓存验证码
        String captchaCache = RedisStringCache.get(captchaId, CacheType.CAPTCHA);
        if(StringUtils.isEmpty(captchaCache)){
            return "请刷新验证码后重新提交";
        }else if(!StringUtils.equalsIgnoreCase(captcha, captchaCache)){
            return "验证码错误";
        }
        RedisStringCache.remove(captchaId, CacheType.CAPTCHA);

        //4.数据库中新增账号信息
        Date date = new Date();
        int res = DbUtil.insertAccount(uid, password,TimeformatUtil.yyyyMMdd(date),TimeformatUtil.yyyyMMddHHmmss(date));
        System.out.println("res: " + res);
        return res > 0 ? "注册成功" : "注册失败";
    }

    @Override
    public Account login(long uid, String password, String captcha, String captchaId) throws Exception {
        //1.入参的合法性验证
        if(StringUtils.isAllBlank(password, captcha, captchaId)){
            return null;
        }

        //2.校验缓存验证码
        String captchaCache = RedisStringCache.get(captchaId, CacheType.CAPTCHA);
        if(StringUtils.isEmpty(captchaCache)){
            return null;
        }else if(!StringUtils.equalsIgnoreCase(captcha, captchaCache)){
            return null;
        }
        RedisStringCache.remove(captchaId, CacheType.CAPTCHA);

        //3.比对数据库用户名和密码
        Account account = DbUtil.queryAccount(uid, password);
        if(account ==  null){
            return null;
        }else{
            //增加唯一ID作为身份标志
            account.setToken(String.valueOf(WhyUuid.getInstance().getUUID()));

            //存入缓存
            RedisStringCache.cache(String.valueOf(account.getToken()), JsonUtil.toJson(account), CacheType.ACCOUNT);

            //更新登录时间
            Date date = new Date();
            DbUtil.updateLoginTime(uid, TimeformatUtil.yyyyMMdd(date),TimeformatUtil.yyyyMMddHHmmss(date));

            return  account;
        }
    }

    @Override
    public boolean accountExitInCache(String token) {
        //
        if(StringUtils.isBlank(token)){
            return false;
        }

        //
        String acc = RedisStringCache.get(token, CacheType.ACCOUNT);
        if(acc != null){
            RedisStringCache.cache(token, acc, CacheType.ACCOUNT);
            return true;
        }else{
            return false;
        }
    }

    //清除缓存登录信息
    @Override
    public boolean logout(String token) {
        RedisStringCache.remove(token, CacheType.ACCOUNT);
        return true;
    }

    //修改密码
    @Override
    public boolean updatePwd(long uid, String oldPwd, String newPwd) {
        int res = DbUtil.updatePwd(uid, oldPwd, newPwd);
        return res == 0 ? false : true;
    }
}
