package com.github.rich.message.listener;

import com.github.rich.common.core.constant.MqQueueConstant;
import com.github.rich.common.core.model.message.ServiceStatusChangeMessage;
import com.github.rich.message.config.RabbitMqCustomConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author Petty
 */
@Slf4j
@Component
@RabbitListener(queues = MqQueueConstant.SERVICE_STATUS_CHANGE_QUEUE)
public class ServiceStatusChangeListener extends AbstractMessageListener<ServiceStatusChangeMessage> {

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
    public boolean send(ServiceStatusChangeMessage message) {
        return super.send(message);
    }
}
