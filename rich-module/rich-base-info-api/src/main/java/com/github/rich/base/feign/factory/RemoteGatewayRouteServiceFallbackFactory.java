package com.github.rich.base.feign.factory;

import com.github.rich.base.feign.RemoteGatewayRouteService;
import com.github.rich.base.feign.fallback.RemoteGatewayRouteServiceFallbackImpl;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @author Petty
 */
@Component
public class RemoteGatewayRouteServiceFallbackFactory implements FallbackFactory<RemoteGatewayRouteService> {
    @Override
    public RemoteGatewayRouteService create(Throwable throwable) {
        RemoteGatewayRouteServiceFallbackImpl gatewayRouteServiceFallback = new RemoteGatewayRouteServiceFallbackImpl();
        gatewayRouteServiceFallback.setCause(throwable);
        return gatewayRouteServiceFallback;
    }
}
