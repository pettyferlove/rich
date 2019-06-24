package com.github.rich.monitor.stream;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * @author Petty
 */
public interface MonitorProcessor {

    String OUTPUT = "rich-monitor-output";

    /**
     * 输出通道
     *
     * @return MessageChannel
     */
    @Output(MonitorProcessor.OUTPUT)
    MessageChannel output();
}
