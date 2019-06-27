package com.github.rich.message.stream;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

/**
 * @author Petty
 */
public interface GatewayProcessor {

    String INPUT = "gateway-change-input";

    String OUTPUT = "gateway-change-output";


    /**
     * 网关路由变更订阅
     *
     * @return SubscribableChannel
     */
    @Input(GatewayProcessor.INPUT)
    SubscribableChannel input();

    /**
     * 输出通道
     *
     * @return MessageChannel
     */
    @Output(GatewayProcessor.OUTPUT)
    MessageChannel output();


}
