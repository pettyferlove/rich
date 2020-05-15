package com.github.rich.log.stream.source;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * @author Petty
 */
public interface UserOperateLogSource {

    String OUTPUT = "user-operate-log-output";

    /**
     * 输出通道
     *
     * @return MessageChannel
     */
    @Output(UserOperateLogSource.OUTPUT)
    MessageChannel output();

}
