package com.github.rich.message.stream.sink;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * @author Petty
 */
public interface CaptchaSmsSink {

    String INPUT = "captcha-sms-input";

    /**
     * 验证码短信消息订阅
     *
     * @return SubscribableChannel
     */
    @Input(CaptchaSmsSink.INPUT)
    SubscribableChannel input();
}
