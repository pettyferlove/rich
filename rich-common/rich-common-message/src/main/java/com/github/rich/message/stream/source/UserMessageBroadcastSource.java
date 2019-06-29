package com.github.rich.message.stream.source;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * @author Petty
 */
public interface UserMessageBroadcastSource {

    String OUTPUT = "user-message-broadcast-output";

    /**
     * 消息广播输出
     *
     * @return MessageChannel
     */
    @Output(UserMessageBroadcastSource.OUTPUT)
    MessageChannel output();

}
