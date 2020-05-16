package com.github.rich.message.service;

import com.github.rich.message.domain.dto.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * @author Petty
 */
public interface IMessageSender extends RabbitTemplate.ReturnCallback{

    /**
     * 发送消息
     * @param routerKey routerKey
     * @param message  消息主体
     */
    void send(String routerKey, Message message);

    /**
     * 发送消息
     * @param exchange exchange
     * @param routerKey routerKey
     * @param message 消息主体
     */
    void send(String exchange, String routerKey, Message message);
}
