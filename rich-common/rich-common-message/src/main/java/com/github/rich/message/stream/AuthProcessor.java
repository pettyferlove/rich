package com.github.rich.message.stream;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

/**
 * @author Petty
 */
public interface AuthProcessor {

    String OUTPUT = "captcha-sms-output";

    String INPUT = "captcha-sms-input";

    /**
     * 输出通道
     *
     * @return MessageChannel
     */
    @Output(AuthProcessor.OUTPUT)
    MessageChannel output();

    /**
     * 验证码短信消息订阅
     *
     * @return SubscribableChannel
     */
    @Input(AuthProcessor.INPUT)
    SubscribableChannel input();
}
