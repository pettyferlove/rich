package com.github.rich.message.stream.sink;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * @author Petty
 */
public interface SensitiveInfoCaptchaSmsSink {

    String INPUT = "sensitive-info-captcha-sms-input";

    /**
     * 敏感信息验证码通道
     *
     * @return SubscribableChannel
     */
    @Input(SensitiveInfoCaptchaSmsSink.INPUT)
    SubscribableChannel input();
}
