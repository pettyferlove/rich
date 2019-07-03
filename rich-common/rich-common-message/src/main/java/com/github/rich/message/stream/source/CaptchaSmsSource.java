package com.github.rich.message.stream.source;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * @author Petty
 */
public interface CaptchaSmsSource {

    String OUTPUT = "captcha-sms-output";

    /**
     * 输出通道
     *
     * @return MessageChannel
     */
    @Output(CaptchaSmsSource.OUTPUT)
    MessageChannel output();

}