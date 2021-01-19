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

import java.util.Date;

/**
 * @Author WHY
 * @Date 2021-01-19
 * @Version 1.0
 */
@Component
public class AccountServiceImpl implements AccountService {
    @Override
    public Account login(long uid, String password, String captcha, String captchaId) throws Exception {
        System.out.println("uid: " + uid + ",password: " + password + ",captcha: " + captcha + ",captchaId: " + captchaId);

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
}
