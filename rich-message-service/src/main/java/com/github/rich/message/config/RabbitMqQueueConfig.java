package com.github.rich.message.config;

import com.github.rich.common.core.constants.RabbitMqQueueConstant;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 创建消息队列
 * @author Petty
 */
@Configuration
public class RabbitMqQueueConfig {

    @Bean
    public Queue createStatusChangeQueue(){
        return new Queue(RabbitMqQueueConstant.SERVICE_STATUS_CHANGE_QUEUE);
    }

    @Bean
    public Exchange createStatusChangeExchange(){
        return new DirectExchange(RabbitMqQueueConstant.SERVICE_STATUS_CHANGE_EXCHANGE);
    }

    @Bean
    public Queue createSmsQueue(){
        return new Queue(RabbitMqQueueConstant.SERVICE_SMS_QUEUE);
    }
}
