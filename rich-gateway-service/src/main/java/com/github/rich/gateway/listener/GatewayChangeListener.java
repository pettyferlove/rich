package com.github.rich.gateway.listener;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.github.rich.base.domain.dto.Route;
import com.github.rich.base.feign.GatewayRouteServiceFeignClient;
import com.github.rich.common.core.constants.CommonConstant;
import com.github.rich.message.domain.dto.message.GatewayRouteChangeMessage;
import com.github.rich.message.domain.dto.message.UserGeneralMessage;
import com.github.rich.message.stream.sink.GatewaySink;
import com.github.rich.message.stream.source.UserMessageSource;
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
@EnableBinding({GatewaySink.class, UserMessageSource.class})
public class GatewayChangeListener {

    private final GatewayRouteServiceFeignClient gatewayRouteServiceFeignClient;

    private final Registration registration;

    private final RouteDefinitionWriter routeDefinitionWriter;

    private final UserMessageSource source;

    public GatewayChangeListener(GatewayRouteServiceFeignClient gatewayRouteServiceFeignClient, Registration registration, RouteDefinitionWriter routeDefinitionWriter, UserMessageSource source) {
        this.gatewayRouteServiceFeignClient = gatewayRouteServiceFeignClient;
        this.registration = registration;
        this.routeDefinitionWriter = routeDefinitionWriter;
        this.source = source;
    }

    @StreamListener(value = GatewaySink.INPUT, condition = "headers['operate-type']=='update'")
    public void routeUpdate(GatewayRouteChangeMessage message) {
        Route route = gatewayRouteServiceFeignClient.load(message.getRouteId());
        StringBuilder sb = new StringBuilder("主机名：");
        StringBuilder messageSb = new StringBuilder("路由更新");
        sb.append(registration.getHost());
        sb.append("，服务名：");
        sb.append(registration.getServiceId());
        sb.append("，实例名：");
        sb.append(registration.getInstanceId());
        UserGeneralMessage userMessage = new UserGeneralMessage();
        userMessage.setDeliver(CommonConstant.SYSTEM_USER_ID);
        userMessage.setReceiver(message.getReceiver());
        try {
            if (ObjectUtil.isNotNull(route) && StringUtils.isNotEmpty(route.getRoute())) {
                if (route.getStatus() == 1) {
                    RouteDefinition routeDefinition = JSON.parseObject(route.getRoute(), RouteDefinition.class);
                    routeDefinition.setId(route.getName());
                    routeDefinitionWriter.save(Mono.just(routeDefinition)).subscribe();
                } else {
                    throw new RuntimeException("route invalid");
                }
            }
            sb.append("网关更新路由：");
            sb.append(route.getName());
            sb.append("请等待待处理请求执行完毕即可生效");
            messageSb.append("成功");
            userMessage.setLevel(1);
        } catch (Exception e) {
            sb.append("路由处理失败");
            messageSb.append("失败");
            userMessage.setLevel(3);
        }
        userMessage.setTime(DateUtil.now());
        userMessage.setType(1);
        userMessage.setMessage(messageSb.toString());
        userMessage.setContent(sb.toString());
        source.output().send(new GenericMessage<>(userMessage));
    }

    @StreamListener(value = GatewaySink.INPUT, condition = "headers['operate-type']=='turnOn'")
    public void routeTurnOn(GatewayRouteChangeMessage message) {
        Route route = gatewayRouteServiceFeignClient.load(message.getRouteId());
        StringBuilder sb = new StringBuilder("主机名：");
        StringBuilder messageSb = new StringBuilder("路由开启");
        sb.append(registration.getHost());
        sb.append("，服务名：");
        sb.append(registration.getServiceId());
        sb.append("，实例名：");
        sb.append(registration.getInstanceId());
        UserGeneralMessage userMessage = new UserGeneralMessage();
        userMessage.setDeliver(CommonConstant.SYSTEM_USER_ID);
        userMessage.setReceiver(message.getReceiver());
        try {
            if (ObjectUtil.isNotNull(route) && StringUtils.isNotEmpty(route.getRoute())) {
                if (route.getStatus() == 1) {
                    RouteDefinition routeDefinition = JSON.parseObject(route.getRoute(), RouteDefinition.class);
                    routeDefinition.setId(route.getName());
                    routeDefinitionWriter.save(Mono.just(routeDefinition)).subscribe();
                } else {
                    throw new RuntimeException("route invalid");
                }
            }
            sb.append("网关添加路由：");
            sb.append(route.getName());
            sb.append("请等待待处理请求执行完毕即可生效");
            messageSb.append("成功");
            userMessage.setLevel(1);
        } catch (Exception e) {
            sb.append("路由处理失败");
            messageSb.append("失败");
            userMessage.setLevel(3);
        }
        userMessage.setTime(DateUtil.now());
        userMessage.setType(1);
        userMessage.setMessage(messageSb.toString());
        userMessage.setContent(sb.toString());
        source.output().send(new GenericMessage<>(userMessage));
    }

    @StreamListener(value = GatewaySink.INPUT, condition = "headers['operate-type']=='shutDown'")
    public void routeShutDown(GatewayRouteChangeMessage message) {
        Route route = gatewayRouteServiceFeignClient.load(message.getRouteId());
        StringBuilder sb = new StringBuilder("主机名：");
        StringBuilder messageSb = new StringBuilder("路由关闭");
        sb.append(registration.getHost());
        sb.append("，服务名：");
        sb.append(registration.getServiceId());
        sb.append("，实例名：");
        sb.append(registration.getInstanceId());
        UserGeneralMessage userMessage = new UserGeneralMessage();
        userMessage.setDeliver(CommonConstant.SYSTEM_USER_ID);
        userMessage.setReceiver(message.getReceiver());
        try {
            if (ObjectUtil.isNotNull(route) && StringUtils.isNotEmpty(route.getRoute())) {
                if (route.getStatus() == 0) {
                    RouteDefinition routeDefinition = JSON.parseObject(route.getRoute(), RouteDefinition.class);
                    routeDefinition.setId(route.getName());
                    routeDefinitionWriter.delete(Mono.just(routeDefinition.getId())).subscribe();
                } else {
                    throw new RuntimeException("route no shutdown");
                }
            }
            sb.append("网关删除路由：");
            sb.append(route.getName());
            sb.append("请等待待处理请求执行完毕即可生效");
            messageSb.append("成功");
            userMessage.setLevel(1);
        } catch (Exception e) {
            sb.append("路由处理失败");
            messageSb.append("失败");
            userMessage.setLevel(3);
        }
        userMessage.setTime(DateUtil.now());
        userMessage.setType(1);
        userMessage.setMessage(messageSb.toString());
        userMessage.setContent(sb.toString());
        source.output().send(new GenericMessage<>(userMessage));
    }

}
