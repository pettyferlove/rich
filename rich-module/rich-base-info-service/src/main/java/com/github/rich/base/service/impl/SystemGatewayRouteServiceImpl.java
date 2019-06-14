package com.github.rich.base.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.rich.base.constants.CacheConstant;
import com.github.rich.base.dto.Route;
import com.github.rich.base.entity.SystemGatewayRoute;
import com.github.rich.base.mapper.SystemGatewayRouteMapper;
import com.github.rich.base.service.ISystemGatewayRouteService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.rich.common.core.exception.BaseRuntimeException;
import com.github.rich.common.core.utils.ConverterUtil;
import com.github.rich.security.utils.SecurityUtil;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * <p>
 * 服务实现类
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
        List<SystemGatewayRoute> systemGatewayRoutes = this.list(Wrappers.<SystemGatewayRoute>lambdaQuery().eq(SystemGatewayRoute::getStatus, "1"));
        Optional<List<Route>> optionalRoutes = Optional.ofNullable(ConverterUtil.convertList(SystemGatewayRoute.class, Route.class, systemGatewayRoutes));
        return optionalRoutes.orElseGet(ArrayList::new);
    }

    @Override
    @Cacheable(value = CacheConstant.OUTER_API_PREFIX + "base-gateway-route-page", key = "T(String).valueOf(#page.current).concat('-').concat(T(String).valueOf(#page.size)).concat('-').concat(#route.toString())")
    public IPage<SystemGatewayRoute> page(SystemGatewayRoute route, Page<SystemGatewayRoute> page) {
        return super.page(page, Wrappers.lambdaQuery(route).orderByDesc(SystemGatewayRoute::getCreateTime));
    }

    @Override
    @Cacheable(value = CacheConstant.OUTER_API_PREFIX + "base-gateway-route-detail", key = "#id", condition = "#id!=null")
    public SystemGatewayRoute get(String id) {
        return this.getById(id);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = CacheConstant.OUTER_API_PREFIX + "base-gateway-route-page", allEntries = true),
            @CacheEvict(value = CacheConstant.INNER_API_PREFIX + "base-api-routes", allEntries = true),
            @CacheEvict(value = CacheConstant.OUTER_API_PREFIX + "base-gateway-route-detail", allEntries = true)
    })
    public Boolean delete(String id) {
        return this.removeById(id);
    }

    @Override
    @CacheEvict(value = CacheConstant.OUTER_API_PREFIX + "base-gateway-route-page", allEntries = true)
    public String create(SystemGatewayRoute route) {
        String routeId = IdUtil.simpleUUID();
        route.setId(routeId);
        route.setCreator(Objects.requireNonNull(SecurityUtil.getUser()).getUserId());
        route.setCreateTime(LocalDateTime.now());
        if (this.save(route)) {
            return routeId;
        } else {
            throw new BaseRuntimeException("新增失败");
        }
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = CacheConstant.OUTER_API_PREFIX + "base-gateway-route-page", allEntries = true),
            @CacheEvict(value = CacheConstant.INNER_API_PREFIX + "base-api-routes", allEntries = true),
            @CacheEvict(value = CacheConstant.OUTER_API_PREFIX + "base-gateway-route-detail", key = "#route.id", condition = "#route.id!=null")
    })
    public Boolean update(SystemGatewayRoute route) {
        route.setModifier(Objects.requireNonNull(SecurityUtil.getUser()).getUserId());
        route.setModifierTime(LocalDateTime.now());
        return this.updateById(route);
    }
}
