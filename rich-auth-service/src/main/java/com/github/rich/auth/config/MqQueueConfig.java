package com.github.rich.auth.config;

import com.github.rich.common.core.constants.MqQueueConstant;
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
    public Queue createSmsQueue(){
        return new Queue(MqQueueConstant.SERVICE_SMS_QUEUE);
    }

}
