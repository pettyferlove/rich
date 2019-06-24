package com.github.rich.monitor.config;

import com.github.rich.common.core.constants.RabbitMqQueueConstant;
import org.springframework.amqp.core.*;
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
    public Binding createStatusChangeExchangeBinding(){
        return BindingBuilder.bind(createStatusChangeQueue()).to(createStatusChangeExchange()).with(RabbitMqQueueConstant.SERVICE_STATUS_CHANGE_QUEUE).noargs();
    }

}
