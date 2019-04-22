package com.github.rich.monitor.config;

import com.github.rich.common.core.constant.MqQueueConstant;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 创建消息队列
 * @author Petty
 */
@Configuration
public class MqQueueConfig {

    @Bean
    public Queue createStatusChangeQueue(){
        return new Queue(MqQueueConstant.SERVICE_STATUS_CHANGE_QUEUE);
    }

}
