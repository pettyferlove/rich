package com.github.rich.message.service.impl;

import com.github.rich.message.config.SmsAliyunProperties;
import com.github.rich.message.dto.message.CaptchaMessage;
import org.springframework.stereotype.Service;


/**
 * 登录验证码发送
 *
 * @author Petty
 */
@Service("loginCaptchaAliyunMessage")
public class LoginCaptchaAliyunMessageImpl extends AbstractAliyunMessageImpl {

    public LoginCaptchaAliyunMessageImpl(SmsAliyunProperties aliyunProperties) {
        super(aliyunProperties);
    }

    @Override
    public boolean send(CaptchaMessage message) {
        return this.sendSms(message, "SMS_169870184");
    }
}
