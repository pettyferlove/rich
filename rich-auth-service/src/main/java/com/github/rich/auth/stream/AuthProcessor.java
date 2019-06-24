package com.github.rich.auth.stream;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * @author Petty
 */
public interface AuthProcessor {

    String OUTPUT = "rich-auth-output";

    /**
     * 输出通道
     *
     * @return MessageChannel
     */
    @Output(AuthProcessor.OUTPUT)
    MessageChannel output();
}
