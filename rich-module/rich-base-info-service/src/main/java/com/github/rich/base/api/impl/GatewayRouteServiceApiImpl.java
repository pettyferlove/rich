package com.github.rich.base.api.impl;

import com.github.rich.base.api.GatewayRouteServiceApi;
import com.github.rich.base.dto.Route;
import com.github.rich.base.service.ISystemGatewayRouteService;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Petty
 */
@RestController
public class GatewayRouteServiceApiImpl implements GatewayRouteServiceApi {

    private final ISystemGatewayRouteService systemGatewayRouteService;

    public GatewayRouteServiceApiImpl(ISystemGatewayRouteService systemGatewayRouteService) {
        this.systemGatewayRouteService = systemGatewayRouteService;
    }

    @Override
    public List<Route> loadRoutes() {
        return systemGatewayRouteService.loadAll();
    }
}
