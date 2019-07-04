package com.github.rich.auth.service.impl;

import com.github.rich.auth.service.AbstractCaptchaValidateService;
import com.github.rich.common.core.constants.SecurityConstant;
import com.github.rich.message.dto.message.CaptchaMessage;
import com.github.rich.message.stream.source.LoginCaptchaSmsSource;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * 短信验证码
 *
 * @author Petty
 */
@Service("smsCaptchaValidateService")
@EnableBinding(LoginCaptchaSmsSource.class)
public class SmsCaptchaValidateServiceImpl extends AbstractCaptchaValidateService {

    private final RedisTemplate redisTemplate;

    private final LoginCaptchaSmsSource source;

    public SmsCaptchaValidateServiceImpl(RedisTemplate redisTemplate, LoginCaptchaSmsSource source) {
        super(redisTemplate);
        this.redisTemplate = redisTemplate;
        this.source = source;
    }

    @Override
    public Boolean create(String code, String captcha, long timeout) {
        boolean result = false;
        StringBuilder stringBuffer = new StringBuilder(SecurityConstant.LOGIN_SMS_CAPTCHA_CODE_KEY);
        stringBuffer.append(":");
        stringBuffer.append(code);
        try {
            if(redisTemplate.opsForValue().setIfAbsent(stringBuffer.toString(), captcha, timeout, TimeUnit.SECONDS)){
                CaptchaMessage captchaMessage = new CaptchaMessage();
                captchaMessage.setReceiver(code);
                captchaMessage.setCaptchaCode(captcha);
                source.output().send(new GenericMessage<>(captchaMessage));
            }
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
        return super.validate(SecurityConstant.LOGIN_SMS_CAPTCHA_CODE_KEY, code, captcha);
    }
}
