package com.github.rich.base.feign.fallback;

import com.github.rich.base.dto.Route;
import com.github.rich.base.feign.RemoteGatewayRouteService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Petty
 */
@Slf4j
@Component
public class RemoteGatewayRouteServiceFallbackImpl implements RemoteGatewayRouteService {

    @Setter
    private Throwable cause;

    @Override
    public List<Route> loadRoutes() {
        log.error("Feign---RemoteGatewayRouteService->loadRoutes Hystrix Fusing->Params:{},Date:{},Cause:{}", null, System.currentTimeMillis(), cause);
        return null;
    }
}
