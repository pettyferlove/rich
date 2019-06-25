package com.github.rich.gateway.stream;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

/**
 * @author Petty
 */
public interface GatewayProcessor {

    String GATEWAY_CHANGE_INPUT = "gateway-change-input";

    String GATEWAY_CHANGE_MESSAGE_OUTPUT = "gateway-change-message-output";

    /**
     * 网关路由变更订阅
     *
     * @return SubscribableChannel
     */
    @Input(GatewayProcessor.GATEWAY_CHANGE_INPUT)
    SubscribableChannel gatewayChangeInput();

    /**
     * 网关路由变更成功提醒订阅
     * @return MessageChannel
     */
    @Output(GatewayProcessor.GATEWAY_CHANGE_MESSAGE_OUTPUT)
    MessageChannel gatewayChangeMessageOutput();

}
