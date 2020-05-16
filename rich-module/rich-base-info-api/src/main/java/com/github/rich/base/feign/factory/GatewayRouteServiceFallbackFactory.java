package com.github.rich.base.feign.factory;

import com.github.rich.base.feign.GatewayRouteServiceFeignClient;
import com.github.rich.base.feign.fallback.GatewayRouteServiceFeignClientFallbackImpl;
import feign.hystrix.FallbackFactory;

/**
 * @author Petty
 */
public class GatewayRouteServiceFallbackFactory implements FallbackFactory<GatewayRouteServiceFeignClient> {
    @Override
    public GatewayRouteServiceFeignClient create(Throwable throwable) {
        GatewayRouteServiceFeignClientFallbackImpl gatewayRouteServiceFallback = new GatewayRouteServiceFeignClientFallbackImpl();
        gatewayRouteServiceFallback.setCause(throwable);
        return gatewayRouteServiceFallback;
    }
}
