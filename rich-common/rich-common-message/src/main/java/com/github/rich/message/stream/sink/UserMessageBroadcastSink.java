package com.github.rich.message.stream.sink;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * @author Petty
 */
public interface UserMessageBroadcastSink {

    String INPUT = "user-message-broadcast-input";

    /**
     * 消息广播输入
     *
     * @return SubscribableChannel
     */
    @Input(UserMessageBroadcastSink.INPUT)
    SubscribableChannel input();
}
