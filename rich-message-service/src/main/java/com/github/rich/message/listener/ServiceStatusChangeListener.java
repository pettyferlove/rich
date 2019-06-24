package com.github.rich.message.listener;

import com.github.rich.common.core.dto.message.ServiceStatusChangeEmailMessage;
import com.github.rich.message.config.RabbitMqCustomConfig;
import com.github.rich.message.stream.MessageProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Petty
 */
@Slf4j
@Component
@EnableBinding(MessageProcessor.class)
public class ServiceStatusChangeListener {

    private final MessageProcessor processor;

    private final RabbitMqCustomConfig rabbitMqCustomConfig;

    public ServiceStatusChangeListener(MessageProcessor processor, RabbitMqCustomConfig rabbitMqCustomConfig) {
        this.processor = processor;
        this.rabbitMqCustomConfig = rabbitMqCustomConfig;
    }

    @StreamListener(MessageProcessor.SERVICE_CHANGE_MESSAGE_INPUT)
    public void handle(ServiceStatusChangeEmailMessage serviceStatusChangeEmailMessage, @Header("x-custom-retry") Integer retry) {
        if (true) {
            System.out.println("send success");
        } else {
            retry(serviceStatusChangeEmailMessage, retry);
        }
        System.out.println("Received: " + serviceStatusChangeEmailMessage);
    }

    public void retry(ServiceStatusChangeEmailMessage serviceStatusChangeEmailMessage, Integer retry) {
        Map<String, Object> headers = new HashMap<>(1);
        if (retry <= rabbitMqCustomConfig.getRetry()) {
            headers.put("x-custom-retry", retry + 1);
            processor.serviceChangeMessageOutput().send(new GenericMessage<>(serviceStatusChangeEmailMessage, headers));
        } else {
            aboveAgain(serviceStatusChangeEmailMessage);
        }
    }

    public void aboveAgain(ServiceStatusChangeEmailMessage serviceStatusChangeEmailMessage) {
        System.out.println("重试结束");
    }
}
