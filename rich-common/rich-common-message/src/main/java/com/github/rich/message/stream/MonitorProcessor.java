package com.github.rich.message.stream;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

/**
 * @author Petty
 */
public interface MonitorProcessor {
    String INPUT = "service-change-message-input";
    String OUTPUT = "service-status-change-output";

    /**
     * 服务状态变更消息订阅
     *
     * @return SubscribableChannel
     */
    @Input(MonitorProcessor.INPUT)
    SubscribableChannel input();

    /**
     * 输出通道
     *
     * @return MessageChannel
     */
    @Output(MonitorProcessor.OUTPUT)
    MessageChannel output();


}
