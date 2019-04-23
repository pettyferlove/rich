package com.github.rich.message.listener;

import com.github.rich.common.core.model.Message;
import com.github.rich.message.config.RabbitMqCustomConfig;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@Slf4j
public class AbstractMessageListener implements MessageListener {

    private final String RETRY_HEADER = "x-custom-retry";

    private final RabbitMqCustomConfig rabbitMqCustomConfig;

    public AbstractMessageListener(RabbitMqCustomConfig rabbitMqCustomConfig) {
        this.rabbitMqCustomConfig = rabbitMqCustomConfig;
    }

    /**
     * 消息处理
     *
     * @param message     封装自定义消息
     * @param channel     通道
     * @param amqpMessage 消息队列消息，用于重试
     */
    @Override
    public void process(Message message, Channel channel, org.springframework.amqp.core.Message amqpMessage) {
        try {
            channel.basicAck(amqpMessage.getMessageProperties().getDeliveryTag(), false);
            if (this.send(message)) {
                log.info("message send success, time: {}", System.currentTimeMillis());
            } else {
                log.info("message send error, time: {}", System.currentTimeMillis());
                Map<String, Object> headers = amqpMessage.getMessageProperties().getHeaders();
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
                    channel.basicPublish(amqpMessage.getMessageProperties().getReceivedExchange(),
                            amqpMessage.getMessageProperties().getReceivedRoutingKey(), properties,
                            amqpMessage.getBody());
                    log.info("重试第{}次", retry);
                } else {
                    // doSomething
                    aboveAgain(message, amqpMessage);
                }
            }
        } catch (Exception e) {
            log.info("message process error, time: {}", System.currentTimeMillis());
            e.printStackTrace();
            try {
                channel.basicNack(amqpMessage.getMessageProperties().getDeliveryTag(), false, true);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    /**
     * 消息发送
     *
     * @param message 封装自定义消息
     * @return 是否成功
     */
    @Override
    public boolean send(Message message) {
        return false;
    }

    /**
     * 消息重试
     *
     * @param message     封装自定义消息
     * @param amqpMessage 消息队列消息
     */
    @Override
    public void aboveAgain(Message message, org.springframework.amqp.core.Message amqpMessage) {
        log.info("超过重试次数，直接忽略");
    }
}
