package com.github.rich.message.stream;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * @author Petty
 */
public interface GatewaySink {

    String INPUT = "gateway-change-input";


    /**
     * 网关路由变更订阅
     *
     * @return SubscribableChannel
     */
    @Input(GatewaySink.INPUT)
    SubscribableChannel input();


}
