package com.github.rich.gateway.listener;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.github.rich.base.dto.Route;
import com.github.rich.base.feign.RemoteGatewayRouteService;
import com.github.rich.message.dto.message.GatewayRouteChangeMessage;
import com.github.rich.message.dto.message.UserGeneralMessage;
import com.github.rich.message.stream.UserMessageProcessor;
import com.github.rich.message.stream.GatewayProcessor;
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

    private final UserMessageProcessor processor;

    public GatewayChangeListener(RemoteGatewayRouteService remoteGatewayRouteService, Registration registration, RouteDefinitionWriter routeDefinitionWriter, UserMessageProcessor processor) {
        this.remoteGatewayRouteService = remoteGatewayRouteService;
        this.registration = registration;
        this.routeDefinitionWriter = routeDefinitionWriter;
        this.processor = processor;
    }

    @StreamListener(value = GatewayProcessor.GATEWAY_CHANGE_INPUT, condition = "headers['operate-type']=='update'")
    public void routeUpdate(GatewayRouteChangeMessage message) {
        Route route = remoteGatewayRouteService.load(message.getRouteId());
        StringBuilder sb = new StringBuilder("主机名：");
        sb.append(registration.getHost());
        sb.append("，服务名：");
        sb.append(registration.getServiceId());
        sb.append("，实例名：");
        sb.append(registration.getInstanceId());
        UserGeneralMessage userMessage = new UserGeneralMessage();
        userMessage.setDeliver("system");
        userMessage.setReceiver(message.getReceiver());
        try {
            if (ObjectUtil.isNotNull(route) && StringUtils.isNotEmpty(route.getRoute())) {
                if (route.getStatus() == 1) {
                    RouteDefinition routeDefinition = JSON.parseObject(route.getRoute(), RouteDefinition.class);
                    routeDefinitionWriter.save(Mono.just(routeDefinition)).subscribe();
                } else {
                    throw new RuntimeException("route invalid");
                }
            }
            sb.append("网关更新路由：");
            sb.append(route.getName());
            sb.append("请等待待处理请求执行完毕即可生效");
            userMessage.setLevel(1);
        } catch (Exception e) {
            sb.append("路由处理失败");
            userMessage.setLevel(3);
        }
        userMessage.setTime(DateUtil.now());
        userMessage.setType(1);
        userMessage.setMessage(sb.toString());
        processor.userGeneralMessageOutput().send(new GenericMessage<>(userMessage));
    }

    @StreamListener(value = GatewayProcessor.GATEWAY_CHANGE_INPUT, condition = "headers['operate-type']=='turnOn'")
    public void routeTurnOn(GatewayRouteChangeMessage message) {
        Route route = remoteGatewayRouteService.load(message.getRouteId());
        StringBuilder sb = new StringBuilder("主机名：");
        sb.append(registration.getHost());
        sb.append("，服务名：");
        sb.append(registration.getServiceId());
        sb.append("，实例名：");
        sb.append(registration.getInstanceId());
        UserGeneralMessage userMessage = new UserGeneralMessage();
        userMessage.setDeliver("system");
        userMessage.setReceiver(message.getReceiver());
        try {
            if (ObjectUtil.isNotNull(route) && StringUtils.isNotEmpty(route.getRoute())) {
                if (route.getStatus() == 1) {
                    RouteDefinition routeDefinition = JSON.parseObject(route.getRoute(), RouteDefinition.class);
                    routeDefinitionWriter.save(Mono.just(routeDefinition)).subscribe();
                } else {
                    throw new RuntimeException("route invalid");
                }
            }
            sb.append("网关添加路由：");
            sb.append(route.getName());
            sb.append("请等待待处理请求执行完毕即可生效");
            userMessage.setLevel(1);
        } catch (Exception e) {
            sb.append("路由处理失败");
            userMessage.setLevel(3);
        }
        userMessage.setTime(DateUtil.now());
        userMessage.setType(1);
        userMessage.setMessage(sb.toString());
        processor.userGeneralMessageOutput().send(new GenericMessage<>(userMessage));
    }

    @StreamListener(value = GatewayProcessor.GATEWAY_CHANGE_INPUT, condition = "headers['operate-type']=='shutDown'")
    public void routeShutDown(GatewayRouteChangeMessage message) {
        Route route = remoteGatewayRouteService.load(message.getRouteId());
        StringBuilder sb = new StringBuilder("主机名：");
        sb.append(registration.getHost());
        sb.append("，服务名：");
        sb.append(registration.getServiceId());
        sb.append("，实例名：");
        sb.append(registration.getInstanceId());
        UserGeneralMessage userMessage = new UserGeneralMessage();
        userMessage.setDeliver("system");
        userMessage.setReceiver(message.getReceiver());
        try {
            if (ObjectUtil.isNotNull(route) && StringUtils.isNotEmpty(route.getRoute())) {
                if (route.getStatus() == 0) {
                    RouteDefinition routeDefinition = JSON.parseObject(route.getRoute(), RouteDefinition.class);
                    routeDefinitionWriter.delete(Mono.just(routeDefinition.getId())).subscribe();
                } else {
                    throw new RuntimeException("route no shutdown");
                }
            }
            sb.append("网关删除路由：");
            sb.append(route.getName());
            sb.append("请等待待处理请求执行完毕即可生效");
            userMessage.setLevel(1);
        } catch (Exception e) {
            sb.append("路由处理失败");
            userMessage.setLevel(3);
        }
        userMessage.setTime(DateUtil.now());
        userMessage.setType(1);
        userMessage.setMessage(sb.toString());
        processor.userGeneralMessageOutput().send(new GenericMessage<>(userMessage));
    }

}
