package com.github.rich.message.listener;

import com.github.rich.common.core.constants.MqQueueConstant;
import com.github.rich.common.core.dto.message.CaptchaMessage;
import com.github.rich.common.core.dto.message.ServiceStatusChangeEmailMessage;
import com.github.rich.message.config.RabbitMqCustomConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author Petty
 */
@Slf4j
@Component
@RabbitListener(queues = MqQueueConstant.SERVICE_STATUS_CHANGE_QUEUE)
public class ServiceStatusChangeListener extends AbstractMessageListener<ServiceStatusChangeEmailMessage> {

    public ServiceStatusChangeListener(RabbitMqCustomConfig rabbitMqCustomConfig) {
        super(rabbitMqCustomConfig);
    }

    /**
     * 消息发送
     *
     * @param message 封装自定义消息
     * @return 是否成功
     */
    @Override
    public boolean send(ServiceStatusChangeEmailMessage message) {
        System.out.println(message);
        return false;
    }

    @Override
    public void aboveAgain(ServiceStatusChangeEmailMessage message, Message amqpMessage) {
        log.info("超出重试次数");
    }
}
