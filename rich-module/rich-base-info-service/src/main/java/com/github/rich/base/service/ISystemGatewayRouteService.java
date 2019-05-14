package com.github.rich.base.service;

import com.github.rich.base.dto.Route;
import com.github.rich.base.entity.SystemGatewayRoute;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Petty
 * @since 2019-05-14
 */
public interface ISystemGatewayRouteService extends IService<SystemGatewayRoute> {

    /**
     * 加载全部Route
     * @return 集合
     */
    List<Route> loadAll();
}
