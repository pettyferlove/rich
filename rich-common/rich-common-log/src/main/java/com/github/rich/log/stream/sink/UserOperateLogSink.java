package com.github.rich.log.stream.sink;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * @author Petty
 */
public interface UserOperateLogSink {

    String INPUT = "user-operate-log-input";

    /**
     * 验证码短信消息订阅
     *
     * @return SubscribableChannel
     */
    @Input(UserOperateLogSink.INPUT)
    SubscribableChannel input();

}
