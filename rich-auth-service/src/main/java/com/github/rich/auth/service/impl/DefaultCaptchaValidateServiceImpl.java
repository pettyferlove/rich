package com.github.rich.auth.service.impl;

import com.github.rich.common.core.constant.SecurityConstant;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;


/**
 * 默认验证码处理
 *
 * @author Petty
 */
@Service("defaultCaptchaValidateService")
public class DefaultCaptchaValidateServiceImpl extends AbstractCaptchaValidateService {

    private final RedisTemplate redisTemplate;

    public DefaultCaptchaValidateServiceImpl(RedisTemplate redisTemplate) {
        super(redisTemplate);
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Boolean create(String code, String captcha, long timeout) {
        boolean result = false;
        StringBuilder stringBuffer = new StringBuilder(SecurityConstant.DEFAULT_CAPTCHA_CODE_KEY);
        stringBuffer.append(":");
        stringBuffer.append(code);
        try {
            redisTemplate.opsForValue().set(stringBuffer.toString(), captcha, timeout, TimeUnit.SECONDS);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //清空
            stringBuffer.setLength(0);
        }
        return result;

    }

    @Override
    public Boolean validate(String code, String captcha) {
        return super.validate(SecurityConstant.DEFAULT_CAPTCHA_CODE_KEY,code, captcha);
    }
}
