package com.github.rich.message.stream;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * @author Petty
 */
public interface UserMessageSource {

    String OUTPUT = "user-general-message-output";

    /**
     * 用户消息输出
     *
     * @return MessageChannel
     */
    @Output(UserMessageSource.OUTPUT)
    MessageChannel output();

}
