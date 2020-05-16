package com.github.rich.base.feign.fallback;

import com.github.rich.base.domain.dto.Route;
import com.github.rich.base.feign.GatewayRouteServiceFeignClient;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author Petty
 */
@Slf4j
public class GatewayRouteServiceFeignClientFallbackImpl implements GatewayRouteServiceFeignClient {

    @Setter
    private Throwable cause;

    @Override
    public List<Route> loadRoutes() {
        log.error("Feign---GatewayRouteServiceFeignClient->loadRoutes Hystrix Fusing->Params:{},Date:{},Cause:{}", null, System.currentTimeMillis(), cause);
        return null;
    }

    @Override
    public Route load(String routeId) {
        log.error("Feign---GatewayRouteServiceFeignClient->load Hystrix Fusing->Params:{},Date:{},Cause:{}", routeId, System.currentTimeMillis(), cause);
        return null;
    }
}
