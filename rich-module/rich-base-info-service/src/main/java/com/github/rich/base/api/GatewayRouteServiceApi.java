package com.github.rich.base.api;

import com.github.rich.base.dto.Route;
import com.github.rich.common.core.constants.CommonConstant;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author Petty
 */
@RequestMapping(CommonConstant.INNER_SERVICE_PREFIX + "/gateway")
public interface GatewayRouteServiceApi {

    /**
     * 加载全部路由定义信息
     * @return Route集合
     */
    @GetMapping("/all")
    List<Route> loadRoutes();

    /**
     * 通过ID查询路由
     * @param routeId routeId
     * @return Route
     */
    @GetMapping("/{routeId}")
    Route load(@PathVariable String routeId);

}
