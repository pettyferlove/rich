package com.github.rich.base.api;

import com.github.rich.base.dto.Route;
import com.github.rich.common.core.constant.CommonConstant;
import org.springframework.web.bind.annotation.PostMapping;
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
    @PostMapping("/load/all")
    List<Route> loadRoutes();

}
