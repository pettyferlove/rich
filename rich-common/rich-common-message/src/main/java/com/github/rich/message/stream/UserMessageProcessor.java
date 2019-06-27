package com.github.rich.message.stream;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

/**
 * @author Petty
 */
public interface UserMessageProcessor {


    String USER_GENERAL_MESSAGE_OUTPUT = "user-general-message-output";

    String USER_GENERAL_MESSAGE_INPUT = "user-general-message-input";

    /**
     * 用户消息输出
     *
     * @return MessageChannel
     */
    @Output(UserMessageProcessor.USER_GENERAL_MESSAGE_OUTPUT)
    MessageChannel userGeneralMessageOutput();

    /**
     * 用户消息接受订阅
     * @return SubscribableChannel
     */
    @Input(UserMessageProcessor.USER_GENERAL_MESSAGE_INPUT)
    SubscribableChannel gatewayChangeMessageInput();
}
