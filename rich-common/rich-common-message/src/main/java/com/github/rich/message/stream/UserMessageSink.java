package com.github.rich.message.stream;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * @author Petty
 */
public interface UserMessageSink {

    String INPUT = "user-general-message-input";

    /**
     * 用户消息接受订阅
     *
     * @return SubscribableChannel
     */
    @Input(UserMessageSink.INPUT)
    SubscribableChannel input();

}
