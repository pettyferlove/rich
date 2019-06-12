package com.github.rich.auth.service.impl;

import com.github.rich.auth.service.AbstractCaptchaValidateService;
import com.github.rich.common.core.constants.MqQueueConstant;
import com.github.rich.common.core.constants.SecurityConstant;
import com.github.rich.common.core.dto.message.CaptchaMessage;
import com.github.rich.common.core.service.IMessageSender;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * 短信验证码
 *
 * @author Petty
 */
@Service("smsCaptchaValidateService")
public class SmsCaptchaValidateServiceImpl extends AbstractCaptchaValidateService {

    private final RedisTemplate redisTemplate;

    private final IMessageSender messageSender;

    public SmsCaptchaValidateServiceImpl(RedisTemplate redisTemplate, IMessageSender messageSender) {
        super(redisTemplate);
        this.redisTemplate = redisTemplate;
        this.messageSender = messageSender;
    }

    @Override
    public Boolean create(String code, String captcha, long timeout) {
        boolean result = false;
        StringBuilder stringBuffer = new StringBuilder(SecurityConstant.SMS_CAPTCHA_CODE_KEY);
        stringBuffer.append(":");
        stringBuffer.append(code);
        try {
            redisTemplate.opsForValue().set(stringBuffer.toString(), captcha, timeout, TimeUnit.SECONDS);
            CaptchaMessage captchaMessage = new CaptchaMessage();
            captchaMessage.setTo(code);
            StringBuilder str = new StringBuilder();
            str.append("登陆账号验证码：");
            str.append(captcha);
            str.append("，请勿转发，转发可能导致盗号。本次验证码有效期为5分钟有效。");
            captchaMessage.setMessage(str.toString());
            messageSender.send(MqQueueConstant.SERVICE_SMS_QUEUE, captchaMessage);
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
        return super.validate(SecurityConstant.SMS_CAPTCHA_CODE_KEY, code, captcha);
    }
}
