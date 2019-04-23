package com.github.rich.message.listener;

import com.github.rich.common.core.model.Message;
import com.rabbitmq.client.Channel;

/**
 * 消息消费者顶层接口
 * @author Petty
 */
public interface MessageListener<T extends Message> {

    /**
     * 消息处理
     * @param message 封装自定义消息
     * @param channel 通道
     * @param amqpMessage 消息队列消息，用于重试
     */
    void process(T message, Channel channel, org.springframework.amqp.core.Message amqpMessage);

    /**
     * 消息发送
     * @param message 封装自定义消息
     * @return 是否成功
     */
    boolean send(T message);

    /**
     * 超出重试限制
     * @param message 封装自定义消息
     * @param amqpMessage 消息队列消息
     */
    void aboveAgain(T message, org.springframework.amqp.core.Message amqpMessage);
}
