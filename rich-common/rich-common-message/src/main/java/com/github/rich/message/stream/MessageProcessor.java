package com.github.rich.message.stream;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * @author Petty
 */
public interface MessageProcessor {

    String SERVICE_CHANGE_MESSAGE_INPUT = "service-change-message-input";

    String CAPTCHA_SMS_MESSAGE_INPUT = "captcha-sms-message-input";


    /**
     * 服务状态变更消息订阅
     *
     * @return SubscribableChannel
     */
    @Input(MessageProcessor.SERVICE_CHANGE_MESSAGE_INPUT)
    SubscribableChannel serviceChangeMessageInput();

    /**
     * 验证码短信消息订阅
     *
     * @return SubscribableChannel
     */
    @Input(MessageProcessor.CAPTCHA_SMS_MESSAGE_INPUT)
    SubscribableChannel captchaSmsMessageInput();


}
