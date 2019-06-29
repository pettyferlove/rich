package com.github.rich.message.stream;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

/**
 * @author Petty
 */
public interface UserMessageBroadcastProcessor {

    String OUTPUT = "user-message-broadcast-output";

    String INPUT = "user-message-broadcast-input";

    /**
     * 消息广播输出
     *
     * @return MessageChannel
     */
    @Output(UserMessageBroadcastProcessor.OUTPUT)
    MessageChannel output();

    /**
     * 消息广播输入
     * @return SubscribableChannel
     */
    @Input(UserMessageBroadcastProcessor.INPUT)
    SubscribableChannel input();
}
