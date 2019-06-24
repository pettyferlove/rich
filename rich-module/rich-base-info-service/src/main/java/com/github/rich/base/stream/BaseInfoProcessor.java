package com.github.rich.base.stream;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * @author Petty
 */
public interface BaseInfoProcessor {

    String OUTPUT = "gateway-change-output";

    /**
     * 输出通道
     *
     * @return MessageChannel
     */
    @Output(BaseInfoProcessor.OUTPUT)
    MessageChannel gatewayChangeOutput();
}
