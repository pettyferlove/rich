package com.github.rich.message.listener;

import com.github.rich.common.core.dto.message.GatewayRouteChangeMessage;
import com.github.rich.message.stream.MessageProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

/**
 * @author Petty
 */
@Slf4j
@Component
@EnableBinding(MessageProcessor.class)
public class GatewayChangeMessageListener {
    @StreamListener(MessageProcessor.SERVICE_CHANGE_MESSAGE_INPUT)
    public void handle(GatewayRouteChangeMessage message) {
        System.out.println(message);
    }
}
