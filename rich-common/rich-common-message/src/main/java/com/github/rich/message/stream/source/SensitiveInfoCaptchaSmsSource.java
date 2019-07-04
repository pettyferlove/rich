package com.github.rich.message.stream.source;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * @author Petty
 */
public interface SensitiveInfoCaptchaSmsSource {

    String OUTPUT = "sensitive-info-captcha-sms-output";

    /**
     * 敏感信息验证码通道
     *
     * @return MessageChannel
     */
    @Output(SensitiveInfoCaptchaSmsSource.OUTPUT)
    MessageChannel output();

}
