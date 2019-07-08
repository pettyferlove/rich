package com.github.rich.gateway.route;

import com.alibaba.fastjson.JSON;
import com.github.rich.base.dto.Route;
import com.github.rich.base.feign.RemoteGatewayRouteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * Gateway启动自动加载Route - Remote
 *
 * @author Petty
 */
@Slf4j
@Component
@Order(0)
public class RemoteLoadRouteRunner implements CommandLineRunner {

    private final RemoteGatewayRouteService remoteGatewayRouteService;

    private final RouteDefinitionWriter routeDefinitionWriter;

    private volatile boolean noInit = true;

    public RemoteLoadRouteRunner(RemoteGatewayRouteService remoteGatewayRouteService, @Qualifier("richInMemoryRouteDefinitionRepository") RouteDefinitionWriter routeDefinitionWriter) {
        this.remoteGatewayRouteService = remoteGatewayRouteService;
        this.routeDefinitionWriter = routeDefinitionWriter;
    }

    @Override
    public void run(String... args) {
        while (noInit) {
            try {
                log.info("start load route");
                List<Route> routes = remoteGatewayRouteService.loadRoutes();
                routes.forEach(route -> {
                    RouteDefinition routeDefinition = JSON.parseObject(route.getRoute(), RouteDefinition.class);
                    routeDefinition.setId(route.getName());
                    routeDefinitionWriter.save(Mono.just(routeDefinition)).subscribe();
                });
                log.info("end load route");
                noInit = false;
            } catch (Exception e) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                log.info("load route error,start retry");
                noInit = true;
            }
        }

    }
}
