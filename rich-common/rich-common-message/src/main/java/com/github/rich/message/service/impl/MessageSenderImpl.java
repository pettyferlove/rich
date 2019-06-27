package com.github.rich.message.service.impl;

import com.github.rich.message.service.IMessageSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

/**
 * @author Petty
 * TODO 消息发送确认机制待完善
 */
@Slf4j
@Service
public class MessageSenderImpl implements IMessageSender {

    private final RabbitTemplate rabbitTemplate;

    public MessageSenderImpl(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    /**
     * Returned message callback.
     *
     * @param message    the returned message.
     * @param replyCode  the reply code.
     * @param replyText  the reply text.
     * @param exchange   the exchange.
     * @param routingKey the routing key.
     */
    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        System.out.println("sender return success" + message.toString() + "===" + replyCode + "===" + exchange + "===" + routingKey);
    }

    @Override
    public void send(String routerKey, com.github.rich.message.dto.Message message) {
        this.rabbitTemplate.setReturnCallback(this);
        this.rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (!ack) {
                log.error("消息发送失败" + cause + correlationData.toString());
            } else {
                if (log.isDebugEnabled()) {
                    System.out.println("消息发送成功 ");
                }
            }
        });
        this.rabbitTemplate.convertAndSend(routerKey, message);
    }

    @Override
    public void send(String exchange, String routerKey, com.github.rich.message.dto.Message message) {
        this.rabbitTemplate.setReturnCallback(this);
        this.rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (!ack) {
                log.error("消息发送失败" + cause + correlationData.toString());
            } else {
                if (log.isDebugEnabled()) {
                    System.out.println("消息发送成功 ");
                }
            }
        });
        this.rabbitTemplate.convertAndSend(exchange, routerKey, message);
    }
}
