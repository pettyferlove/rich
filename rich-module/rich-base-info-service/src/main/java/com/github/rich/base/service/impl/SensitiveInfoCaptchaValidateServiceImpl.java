package com.github.rich.base.service.impl;

import com.github.rich.common.core.constants.SecurityConstant;
import com.github.rich.message.domain.dto.message.CaptchaMessage;
import com.github.rich.message.stream.source.SensitiveInfoCaptchaSmsSource;
import com.github.rich.security.service.AbstractCaptchaValidateService;
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
@Service("sensitiveInfoCaptchaValidateService")
@EnableBinding(SensitiveInfoCaptchaSmsSource.class)
public class SensitiveInfoCaptchaValidateServiceImpl extends AbstractCaptchaValidateService {

    private final RedisTemplate redisTemplate;

    private final SensitiveInfoCaptchaSmsSource source;

    public SensitiveInfoCaptchaValidateServiceImpl(RedisTemplate redisTemplate, SensitiveInfoCaptchaSmsSource source) {
        super(redisTemplate);
        this.redisTemplate = redisTemplate;
        this.source = source;
    }

    @Override
    public Boolean create(String code, String captcha, long timeout) {
        boolean result = false;
        StringBuilder stringBuffer = new StringBuilder(SecurityConstant.SENSITIVE_INFO_CAPTCHA_CODE_KEY);
        stringBuffer.append(":");
        stringBuffer.append(code);
        try {
            if (redisTemplate.opsForValue().setIfAbsent(stringBuffer.toString(), captcha, timeout, TimeUnit.SECONDS)) {
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
        return super.validate(SecurityConstant.SENSITIVE_INFO_CAPTCHA_CODE_KEY, code, captcha);
    }
}
