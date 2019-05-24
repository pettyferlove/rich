package com.github.rich.base.service.impl;

import com.github.rich.base.constant.CacheConstant;
import com.github.rich.base.dto.Route;
import com.github.rich.base.entity.SystemGatewayRoute;
import com.github.rich.base.mapper.SystemGatewayRouteMapper;
import com.github.rich.base.service.ISystemGatewayRouteService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.rich.common.core.utils.ConverterUtil;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Petty
 * @since 2019-05-14
 */
@Service
public class SystemGatewayRouteServiceImpl extends ServiceImpl<SystemGatewayRouteMapper, SystemGatewayRoute> implements ISystemGatewayRouteService {

    @Override
    @Cacheable(value = CacheConstant.INNER_API_PREFIX + "base-api-routes")
    public List<Route> loadAll() {
        List<SystemGatewayRoute> systemGatewayRoutes = this.list();
        Optional<List<Route>> optionalRoutes = Optional.ofNullable(ConverterUtil.convertList(SystemGatewayRoute.class, Route.class, systemGatewayRoutes));
        return optionalRoutes.orElseGet(ArrayList::new);
    }
}
