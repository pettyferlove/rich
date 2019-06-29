package com.github.rich.message.stream.sink;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * @author Petty
 */
public interface ServiceMonitorSink {

    String INPUT = "service-status-change-input";

    /**
     * 服务状态变更消息订阅
     *
     * @return SubscribableChannel
     */
    @Input(ServiceMonitorSink.INPUT)
    SubscribableChannel input();


}
