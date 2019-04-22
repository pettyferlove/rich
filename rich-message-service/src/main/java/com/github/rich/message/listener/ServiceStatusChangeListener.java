package com.github.rich.message.listener;

import com.github.rich.common.core.constant.MqQueueConstant;
import com.github.rich.common.core.model.message.ServiceStatusChangeMessage;
import com.github.rich.message.config.RabbitMqCustomConfig;
import com.github.rich.message.service.IMailService;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

/**
 * @author Petty
 */
@Slf4j
@Component
@RabbitListener(queues = MqQueueConstant.SERVICE_STATUS_CHANGE_QUEUE)
public class ServiceStatusChangeListener {

    private final String RETRY_HEADER = "x-custom-retry";

    private final IMailService mailService;

    private final RabbitMqCustomConfig rabbitMqCustomConfig;

    public ServiceStatusChangeListener(IMailService mailService, RabbitMqCustomConfig rabbitMqCustomConfig) {
        this.mailService = mailService;
        this.rabbitMqCustomConfig = rabbitMqCustomConfig;
    }

    @RabbitHandler
    public void process(ServiceStatusChangeMessage serviceStatusChangeMessage, Channel channel, Message message) {
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            if (mailService.send(serviceStatusChangeMessage)) {
                log.info("mail send success, time: {}", System.currentTimeMillis());
            } else {
                log.info("mail send error, time: {}", System.currentTimeMillis());
                Map<String, Object> headers = message.getMessageProperties().getHeaders();
                // 重试次数
                Optional<Object> retryOptional = Optional.ofNullable(headers.get(RETRY_HEADER));
                String retryString;
                if (retryOptional.isPresent()) {
                    retryString = String.valueOf(retryOptional.get());
                } else {
                    retryString = "0";
                }
                Integer retry = Integer.valueOf(retryString);
                if (retry <= rabbitMqCustomConfig.getRetry()) {
                    // 写入重试头
                    if (headers.containsKey(RETRY_HEADER)) {
                        // 一条消息在单线程执行
                        headers.put(RETRY_HEADER, retry + 1);
                    } else {
                        // 默认为1
                        headers.put(RETRY_HEADER, 1);
                    }
                    AMQP.BasicProperties properties = new AMQP.BasicProperties("application/x-java-serialized-object",
                            null,
                            headers,
                            2,
                            0, null, null, null,
                            null, null, null, null,
                            null, null);
                    channel.basicPublish(message.getMessageProperties().getReceivedExchange(),
                            message.getMessageProperties().getReceivedRoutingKey(), properties,
                            message.getBody());
                    log.info("重试第{}次", retry);
                } else {
                    // doSomething
                    log.info("超过重试次数，直接忽略");
                }
            }
        } catch (Exception e) {
            log.info("mail process error, time: {}", System.currentTimeMillis());
            e.printStackTrace();
            try {
                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

    }
}
