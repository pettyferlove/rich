package com.github.rich.gateway.listener;

import com.github.rich.common.core.dto.message.GatewayRouteChangeMessage;
import com.github.rich.gateway.stream.GatewayProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

/**
 * @author Petty
 */
@Slf4j
@Component
@EnableBinding(GatewayProcessor.class)
public class GatewayChangeListener {

    @StreamListener(value = GatewayProcessor.GATEWAY_CHANGE_INPUT, condition = "headers['operate-type']=='update'")
    public void routeUpdate(GatewayRouteChangeMessage message){
        System.out.println(message);
    }

    @StreamListener(value = GatewayProcessor.GATEWAY_CHANGE_INPUT, condition = "headers['operate-type']=='turnOn'")
    public void routeTurnOn(GatewayRouteChangeMessage message){
        System.out.println(message);
    }

    @StreamListener(value = GatewayProcessor.GATEWAY_CHANGE_INPUT, condition = "headers['operate-type']=='shutDown'")
    public void routeShutDown(GatewayRouteChangeMessage message){
        System.out.println(message);
    }

}
