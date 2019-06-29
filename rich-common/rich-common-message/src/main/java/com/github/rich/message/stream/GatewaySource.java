package com.github.rich.message.stream;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * @author Petty
 */
public interface GatewaySource {

    String OUTPUT = "gateway-change-output";

    /**
     * 输出通道
     *
     * @return MessageChannel
     */
    @Output(GatewaySource.OUTPUT)
    MessageChannel output();


}
