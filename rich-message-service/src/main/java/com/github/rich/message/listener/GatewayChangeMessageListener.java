package com.github.rich.message.listener;

import com.github.rich.common.core.dto.message.GatewayRouteChangeStatusMessage;
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
    @StreamListener(MessageProcessor.GATEWAY_CHANGE_MESSAGE_INPUT)
    public void handle(GatewayRouteChangeStatusMessage message) {
        System.out.println(message);
    }
}
