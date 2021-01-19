package com.why.counter.controller;

import com.why.counter.bean.res.Account;
import com.why.counter.bean.res.CaptchaRes;
import com.why.counter.bean.res.CounterRes;
import com.why.counter.cache.CacheType;
import com.why.counter.cache.RedisStringCache;
import com.why.counter.service.AccountService;
import com.why.counter.thirdpart.uuid.WhyUuid;
import com.why.counter.util.Captcha;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.why.counter.bean.res.CounterRes.FAIL;
import static com.why.counter.bean.res.CounterRes.RELOGIN;

/**
 * @Author WHY
 * @Date 2021-01-14
 * @Version 1.0
 */
@RestController
@RequestMapping("/login")
@Log4j2
public class LoginController {
    @Autowired
    private AccountService accountService;

    @RequestMapping("/captcha")
    public CounterRes captcha() throws Exception{
        //1.生成验证码 120 40 4个字符 10个线条
        Captcha captcha = new Captcha(120,40,4,10);

        //2.将验证码<ID,验证码数值>放入缓存
        String uuid = String.valueOf(WhyUuid.getInstance().getUUID());
        //注入缓存
        RedisStringCache.cache(uuid, captcha.getCode(), CacheType.CAPTCHA);

        //3.使用base64编码图片，并返回给前台
        //uuid,base64
        CaptchaRes res = new CaptchaRes(uuid, captcha.getBase64ByteStr());

        return new CounterRes(res);
    }

    @RequestMapping("/userlogin")
    public CounterRes login(@RequestParam long uid, @RequestParam String password, @RequestParam String captcha, @RequestParam String captchaId) throws Exception{
        //
        Account account = accountService.login(uid,password,captcha,captchaId);

        if(account == null){
            return new CounterRes(FAIL,"用户名密码/验证码错误，登录失败",null);
        }else{
            return new CounterRes(account);
        }
    }

    @RequestMapping("/loginfail")
    public CounterRes loginFail(){
        return new CounterRes(RELOGIN,"请重新登录", null);
    }


}
