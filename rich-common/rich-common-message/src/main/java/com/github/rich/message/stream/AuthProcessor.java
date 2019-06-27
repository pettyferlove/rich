package com.github.rich.message.stream;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * @author Petty
 */
public interface AuthProcessor {

    String OUTPUT = "captcha-sms-output";

    /**
     * 输出通道
     *
     * @return MessageChannel
     */
    @Output(AuthProcessor.OUTPUT)
    MessageChannel output();
}
