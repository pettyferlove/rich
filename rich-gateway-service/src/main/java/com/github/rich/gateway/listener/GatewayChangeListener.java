package com.github.rich.gateway.listener;

import com.github.rich.base.dto.Route;
import com.github.rich.base.feign.RemoteGatewayRouteService;
import com.github.rich.common.core.dto.message.GatewayRouteChangeMessage;
import com.github.rich.gateway.stream.GatewayProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
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

    private final RemoteGatewayRouteService remoteGatewayRouteService;

    private final Registration registration;

    private final RouteDefinitionWriter routeDefinitionWriter;

    public GatewayChangeListener(RemoteGatewayRouteService remoteGatewayRouteService, Registration registration, RouteDefinitionWriter routeDefinitionWriter) {
        this.remoteGatewayRouteService = remoteGatewayRouteService;
        this.registration = registration;
        this.routeDefinitionWriter = routeDefinitionWriter;
    }

    @StreamListener(value = GatewayProcessor.GATEWAY_CHANGE_INPUT, condition = "headers['operate-type']=='update'")
    public void routeUpdate(GatewayRouteChangeMessage message){
        Route route = remoteGatewayRouteService.load(message.getRouteId());
        System.out.println(route);
        System.out.println(message);
        System.out.println(registration.getInstanceId());
        System.out.println(registration.getHost());
        System.out.println(registration.getScheme());
        System.out.println(registration.getServiceId());
        System.out.println(registration.getPort());
    }

    @StreamListener(value = GatewayProcessor.GATEWAY_CHANGE_INPUT, condition = "headers['operate-type']=='turnOn'")
    public void routeTurnOn(GatewayRouteChangeMessage message){
        Route route = remoteGatewayRouteService.load(message.getRouteId());
        System.out.println(route);
        System.out.println(message);
        System.out.println(registration.getInstanceId());
        System.out.println(registration.getHost());
        System.out.println(registration.getScheme());
        System.out.println(registration.getServiceId());
        System.out.println(registration.getPort());
    }

    @StreamListener(value = GatewayProcessor.GATEWAY_CHANGE_INPUT, condition = "headers['operate-type']=='shutDown'")
    public void routeShutDown(GatewayRouteChangeMessage message){
        Route route = remoteGatewayRouteService.load(message.getRouteId());
        System.out.println(route);
        System.out.println(message);
        System.out.println(registration.getInstanceId());
        System.out.println(registration.getHost());
        System.out.println(registration.getScheme());
        System.out.println(registration.getServiceId());
        System.out.println(registration.getPort());
    }

}
