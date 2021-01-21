package com.why.counter.service;

import com.why.counter.bean.res.Account;
import com.why.counter.util.DbUtil;

/**
 * @Author WHY
 * @Date 2021-01-19
 * @Version 1.0
 */
public interface AccountService {
    /**
     * 注册
     * @param uid
     * @param password
     * @param captcha
     * @param captchaId
     * @return
     * @throws Exception
     */
    String register(long uid, String password, String captcha, String captchaId) throws Exception;

    /**
     * 登录
     * @param uid
     * @param password
     * @param captcha
     * @param captchaId
     * @return
     * @throws Exception
     */
    Account login(long uid, String password, String captcha, String captchaId) throws Exception;

    /**
     * 缓存中是否已存在登录信息
     * @param token
     * @return
     */
    boolean accountExitInCache(String token);

    /**
     * 退出登录
     * @param token
     * @return
     */
    boolean logout(String token);


    /**
     * 修改密码
     * @param uid
     * @param oldPwd
     * @param newPwd
     * @return
     */
    boolean updatePwd(long uid, String oldPwd, String newPwd);
}
