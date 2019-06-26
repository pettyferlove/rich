package com.github.rich.gateway.listener;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.github.rich.base.dto.Route;
import com.github.rich.base.feign.RemoteGatewayRouteService;
import com.github.rich.common.core.constants.CommonConstant;
import com.github.rich.common.core.dto.message.GatewayRouteChangeMessage;
import com.github.rich.common.core.dto.message.GatewayRouteChangeStatusMessage;
import com.github.rich.gateway.stream.GatewayProcessor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;


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

    private final GatewayProcessor processor;

    public GatewayChangeListener(RemoteGatewayRouteService remoteGatewayRouteService, Registration registration, RouteDefinitionWriter routeDefinitionWriter, GatewayProcessor processor) {
        this.remoteGatewayRouteService = remoteGatewayRouteService;
        this.registration = registration;
        this.routeDefinitionWriter = routeDefinitionWriter;
        this.processor = processor;
    }

    @StreamListener(value = GatewayProcessor.GATEWAY_CHANGE_INPUT, condition = "headers['operate-type']=='update'")
    public void routeUpdate(GatewayRouteChangeMessage message){
        Route route = remoteGatewayRouteService.load(message.getRouteId());
        GatewayRouteChangeStatusMessage gatewayRouteChangeStatusMessage = new GatewayRouteChangeStatusMessage();
        gatewayRouteChangeStatusMessage.setReceiver(message.getReceiver());
        gatewayRouteChangeStatusMessage.setDeliver("system");
        gatewayRouteChangeStatusMessage.setMessage("更新成功");
        gatewayRouteChangeStatusMessage.setRouteName(route.getName());
        gatewayRouteChangeStatusMessage.setTime(DateUtil.now());
        gatewayRouteChangeStatusMessage.setInstanceId(registration.getInstanceId());
        gatewayRouteChangeStatusMessage.setServiceId(registration.getServiceId());
        gatewayRouteChangeStatusMessage.setPort(String.valueOf(registration.getPort()));
        gatewayRouteChangeStatusMessage.setHost(registration.getHost());
        try{
            if(ObjectUtil.isNotNull(route)&& StringUtils.isNotEmpty(route.getRoute())){
                if(route.getStatus()==1){
                    RouteDefinition routeDefinition = JSON.parseObject(route.getRoute(), RouteDefinition.class);
                    routeDefinitionWriter.save(Mono.just(routeDefinition)).subscribe();
                }else{
                    throw new RuntimeException("route invalid");
                }
            }
            gatewayRouteChangeStatusMessage.setStatus(CommonConstant.GATEWAY_ROUTE_UPDATE_OK);
        }catch (Exception e){
            gatewayRouteChangeStatusMessage.setStatus(CommonConstant.GATEWAY_ROUTE_CHANGE_FAIL);
        }
        processor.gatewayChangeMessageOutput().send(new GenericMessage<>(gatewayRouteChangeStatusMessage));
    }

    @StreamListener(value = GatewayProcessor.GATEWAY_CHANGE_INPUT, condition = "headers['operate-type']=='turnOn'")
    public void routeTurnOn(GatewayRouteChangeMessage message){
        Route route = remoteGatewayRouteService.load(message.getRouteId());
        GatewayRouteChangeStatusMessage gatewayRouteChangeStatusMessage = new GatewayRouteChangeStatusMessage();
        gatewayRouteChangeStatusMessage.setDeliver("system");
        gatewayRouteChangeStatusMessage.setMessage("开启成功");
        gatewayRouteChangeStatusMessage.setReceiver(message.getReceiver());
        gatewayRouteChangeStatusMessage.setRouteName(route.getName());
        gatewayRouteChangeStatusMessage.setTime(DateUtil.now());
        gatewayRouteChangeStatusMessage.setInstanceId(registration.getInstanceId());
        gatewayRouteChangeStatusMessage.setServiceId(registration.getServiceId());
        gatewayRouteChangeStatusMessage.setPort(String.valueOf(registration.getPort()));
        gatewayRouteChangeStatusMessage.setHost(registration.getHost());
        try{
            if(ObjectUtil.isNotNull(route)&& StringUtils.isNotEmpty(route.getRoute())){
                if(route.getStatus()==1){
                    RouteDefinition routeDefinition = JSON.parseObject(route.getRoute(), RouteDefinition.class);
                    routeDefinitionWriter.save(Mono.just(routeDefinition)).subscribe();
                }else{
                    throw new RuntimeException("route invalid");
                }
            }
            gatewayRouteChangeStatusMessage.setStatus(CommonConstant.GATEWAY_ROUTE_ADD_OK);
        }catch (Exception e){
            gatewayRouteChangeStatusMessage.setStatus(CommonConstant.GATEWAY_ROUTE_CHANGE_FAIL);
        }
        processor.gatewayChangeMessageOutput().send(new GenericMessage<>(gatewayRouteChangeStatusMessage));
    }

    @StreamListener(value = GatewayProcessor.GATEWAY_CHANGE_INPUT, condition = "headers['operate-type']=='shutDown'")
    public void routeShutDown(GatewayRouteChangeMessage message){
        Route route = remoteGatewayRouteService.load(message.getRouteId());
        GatewayRouteChangeStatusMessage gatewayRouteChangeStatusMessage = new GatewayRouteChangeStatusMessage();
        gatewayRouteChangeStatusMessage.setReceiver(message.getReceiver());
        gatewayRouteChangeStatusMessage.setDeliver("system");
        gatewayRouteChangeStatusMessage.setMessage("关闭成功");
        gatewayRouteChangeStatusMessage.setRouteName(route.getName());
        gatewayRouteChangeStatusMessage.setTime(DateUtil.now());
        gatewayRouteChangeStatusMessage.setInstanceId(registration.getInstanceId());
        gatewayRouteChangeStatusMessage.setServiceId(registration.getServiceId());
        gatewayRouteChangeStatusMessage.setPort(String.valueOf(registration.getPort()));
        gatewayRouteChangeStatusMessage.setHost(registration.getHost());
        try{
            if(ObjectUtil.isNotNull(route)&& StringUtils.isNotEmpty(route.getRoute())){
                if(route.getStatus()==0){
                    RouteDefinition routeDefinition = JSON.parseObject(route.getRoute(), RouteDefinition.class);
                    routeDefinitionWriter.delete(Mono.just(routeDefinition.getId())).subscribe();
                }else{
                    throw new RuntimeException("route no shutdown");
                }
            }
            gatewayRouteChangeStatusMessage.setStatus(CommonConstant.GATEWAY_ROUTE_DELETE_OK);
        }catch (Exception e){
            gatewayRouteChangeStatusMessage.setStatus(CommonConstant.GATEWAY_ROUTE_CHANGE_FAIL);
        }
        processor.gatewayChangeMessageOutput().send(new GenericMessage<>(gatewayRouteChangeStatusMessage));
    }

}
