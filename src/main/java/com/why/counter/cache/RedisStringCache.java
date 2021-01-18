package com.why.counter.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

/**
 * @Author WHY
 * @Date 2021-01-15
 * @Version 1.0
 */
@Component
public class RedisStringCache {

    //调用缓存类的静态方法来获取
    private static RedisStringCache redisStringCache;

    //把构造函数私有化
    private RedisStringCache(){}

    @Autowired
    private StringRedisTemplate template;

    @Value("${cacheexpire.captcha}")
    private int captchaExpireTime;

    @Value("${cacheexpire.account}")
    private int accountExpireTime;

    @Value("${cacheexpire.order}")
    private int orderExpireTime;

    public StringRedisTemplate getTemplate() {
        return template;
    }

    public void setTemplate(StringRedisTemplate template) {
        this.template = template;
    }

    public int getCaptchaExpireTime() {
        return captchaExpireTime;
    }

    public void setCaptchaExpireTime(int captchaExpireTime) {
        this.captchaExpireTime = captchaExpireTime;
    }

    public int getAccountExpireTime() {
        return accountExpireTime;
    }

    public void setAccountExpireTime(int accountExpireTime) {
        this.accountExpireTime = accountExpireTime;
    }

    public int getOrderExpireTime() {
        return orderExpireTime;
    }

    public void setOrderExpireTime(int orderExpireTime) {
        this.orderExpireTime = orderExpireTime;
    }

    @PostConstruct
    private void init(){
        //
        redisStringCache = new RedisStringCache();
        redisStringCache.setTemplate(template);
        redisStringCache.setCaptchaExpireTime(captchaExpireTime);
        redisStringCache.setAccountExpireTime(accountExpireTime);
        redisStringCache.setOrderExpireTime(orderExpireTime);
    }

    /**
     * 添加缓存
     * @param key
     * @param value
     * @param cacheType
     */
    public static void cache(String key, String value, CacheType cacheType){
        int expireTime;

        switch (cacheType){
            case ACCOUNT:
                expireTime = redisStringCache.getAccountExpireTime();
                break;
            case CAPTCHA:
                expireTime = redisStringCache.getCaptchaExpireTime();
                break;
            case ORDER:
            case TRADE:
            case POSI:
                expireTime = redisStringCache.getOrderExpireTime();
                break;
            default:
                expireTime = 10;
        }

        redisStringCache.getTemplate().opsForValue().set(cacheType.type() + key, value, expireTime, TimeUnit.SECONDS);
    }


    /**
     * 查询缓存
     * @param key
     * @param cacheType
     * @return
     */
    public static String get(String key, CacheType cacheType){
        return redisStringCache.getTemplate().opsForValue().get(cacheType.type() + key);
    }


    /**
     * 删除缓存
     * @param key
     * @param cacheType
     */
    public static void remove(String key, CacheType cacheType){
         redisStringCache.getTemplate().delete(cacheType.type() + key);
    }

}
