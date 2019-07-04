package com.github.rich.message.stream.source;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * @author Petty
 */
public interface LoginCaptchaSmsSource {

    String OUTPUT = "login-captcha-sms-output";

    /**
     * 输出通道
     *
     * @return MessageChannel
     */
    @Output(LoginCaptchaSmsSource.OUTPUT)
    MessageChannel output();

}
