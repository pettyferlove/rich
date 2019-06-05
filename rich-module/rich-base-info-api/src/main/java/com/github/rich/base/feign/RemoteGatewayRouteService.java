package com.github.rich.base.feign;

import com.github.rich.base.dto.Route;
import com.github.rich.base.feign.factory.RemoteGatewayRouteServiceFallbackFactory;
import com.github.rich.common.core.constants.CommonConstant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @author Petty
 */

@FeignClient(name = "rich-base-info-service", fallbackFactory = RemoteGatewayRouteServiceFallbackFactory.class)
public interface RemoteGatewayRouteService {

    /**
     * 加载全部路由定义信息
     *
     * @return Route集合
     */
    @GetMapping(value = CommonConstant.INNER_SERVICE_PREFIX + "/gateway/all")
    List<Route> loadRoutes();

}
