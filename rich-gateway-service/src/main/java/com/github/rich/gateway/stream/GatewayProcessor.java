package com.github.rich.gateway.stream;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * @author Petty
 */
public interface GatewayProcessor {

    String GATEWAY_CHANGE_INPUT = "gateway-change-input";

    /**
     * 网关路由变更订阅
     *
     * @return SubscribableChannel
     */
    @Input(GatewayProcessor.GATEWAY_CHANGE_INPUT)
    SubscribableChannel gatewayChangeInput();


}
