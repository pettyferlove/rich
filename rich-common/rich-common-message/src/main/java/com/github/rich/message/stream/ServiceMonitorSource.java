package com.github.rich.message.stream;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * @author Petty
 */
public interface ServiceMonitorSource {

    String OUTPUT = "service-status-change-output";

    /**
     * 输出通道
     *
     * @return MessageChannel
     */
    @Output(ServiceMonitorSource.OUTPUT)
    MessageChannel output();

}
