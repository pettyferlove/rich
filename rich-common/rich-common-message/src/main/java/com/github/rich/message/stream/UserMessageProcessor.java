package com.github.rich.message.stream;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

/**
 * @author Petty
 */
public interface UserMessageProcessor {


    String OUTPUT = "user-general-message-output";

    String INPUT = "user-general-message-input";

    /**
     * 用户消息输出
     *
     * @return MessageChannel
     */
    @Output(UserMessageProcessor.OUTPUT)
    MessageChannel output();

    /**
     * 用户消息接受订阅
     * @return SubscribableChannel
     */
    @Input(UserMessageProcessor.INPUT)
    SubscribableChannel input();
}
