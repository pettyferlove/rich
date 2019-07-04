package com.github.rich.message.service.impl;

import com.github.rich.message.config.SmsAliyunProperties;
import com.github.rich.message.dto.message.CaptchaMessage;
import org.springframework.stereotype.Service;


/**
 * 用户敏感数据修改验证码
 * @author Petty
 */
@Service("sensitiveInfoCaptchaAliyunMessage")
public class SensitiveInfoCaptchaAliyunMessageImpl extends AbstractAliyunMessageImpl {

    public SensitiveInfoCaptchaAliyunMessageImpl(SmsAliyunProperties aliyunProperties) {
        super(aliyunProperties);
    }

    @Override
    public boolean send(CaptchaMessage message) {
        return this.sendSms(message,"SMS_169870160");
    }


}
