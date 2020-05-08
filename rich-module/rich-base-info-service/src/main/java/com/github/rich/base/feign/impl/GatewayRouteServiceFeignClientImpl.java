package com.github.rich.base.feign.impl;

import com.github.rich.base.dto.Route;
import com.github.rich.base.feign.GatewayRouteServiceFeignClient;
import com.github.rich.base.service.ISystemGatewayRouteService;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Petty
 */
@RestController
public class GatewayRouteServiceFeignClientImpl implements GatewayRouteServiceFeignClient {

    private final ISystemGatewayRouteService systemGatewayRouteService;

    public GatewayRouteServiceFeignClientImpl(ISystemGatewayRouteService systemGatewayRouteService) {
        this.systemGatewayRouteService = systemGatewayRouteService;
    }

    @Override
    public List<Route> loadRoutes() {
        return systemGatewayRouteService.loadAll();
    }

    @Override
    public Route load(String routeId) {
        return systemGatewayRouteService.load(routeId);
    }
}
