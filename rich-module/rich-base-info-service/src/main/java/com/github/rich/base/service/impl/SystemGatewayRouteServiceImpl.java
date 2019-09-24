package com.github.rich.base.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.rich.base.constants.CacheConstant;
import com.github.rich.base.dto.Route;
import com.github.rich.base.entity.SystemGatewayRoute;
import com.github.rich.base.mapper.SystemGatewayRouteMapper;
import com.github.rich.base.service.ISystemGatewayRouteService;
import com.github.rich.common.core.constants.CommonConstant;
import com.github.rich.common.core.exception.BaseRuntimeException;
import com.github.rich.common.core.utils.ConverterUtil;
import com.github.rich.message.dto.message.GatewayRouteChangeMessage;
import com.github.rich.message.stream.source.GatewaySource;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
@EnableBinding(GatewaySource.class)
public class SystemGatewayRouteServiceImpl extends ServiceImpl<SystemGatewayRouteMapper, SystemGatewayRoute> implements ISystemGatewayRouteService {

    private final GatewaySource source;

    public SystemGatewayRouteServiceImpl(GatewaySource source) {
        this.source = source;
    }

    @Override
    public List<Route> loadAll() {
        List<SystemGatewayRoute> systemGatewayRoutes = this.list(Wrappers.<SystemGatewayRoute>lambdaQuery().eq(SystemGatewayRoute::getStatus, "1"));
        Optional<List<Route>> optionalRoutes = Optional.ofNullable(ConverterUtil.convertList(SystemGatewayRoute.class, Route.class, systemGatewayRoutes));
        return optionalRoutes.orElseGet(ArrayList::new);
    }

    @Override
    public Route load(String routeId) {
        SystemGatewayRoute systemGatewayRoute = this.getOne(Wrappers.<SystemGatewayRoute>lambdaQuery().eq(SystemGatewayRoute::getId, routeId));
        Optional<Route> routeOptional = Optional.ofNullable(ConverterUtil.convert(systemGatewayRoute, new Route()));
        return routeOptional.orElseGet(Route::new);
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
    public String create(String userId, SystemGatewayRoute route) {
        String routeId = IdUtil.simpleUUID();
        route.setId(routeId);
        route.setCreator(userId);
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
    public Boolean update(String userId, SystemGatewayRoute route) {
        boolean result;
        route.setModifier(userId);
        route.setModifyTime(LocalDateTime.now());
        result = this.updateById(route);
        if (route.getStatus() == 1 && result) {
            GatewayRouteChangeMessage message = new GatewayRouteChangeMessage();
            message.setRouteId(route.getId());
            message.setReceiver(userId);
            message.setDeliver(CommonConstant.SYSTEM_USER_ID);
            source.output().send(MessageBuilder.withPayload(message).setHeader("operate-type", "update").build());
        }
        return result;
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = CacheConstant.OUTER_API_PREFIX + "base-gateway-route-page", allEntries = true),
            @CacheEvict(value = CacheConstant.INNER_API_PREFIX + "base-api-routes", allEntries = true),
            @CacheEvict(value = CacheConstant.OUTER_API_PREFIX + "base-gateway-route-detail", key = "#route.id", condition = "#route.id!=null")
    })
    public Boolean changeStatus(String userId, SystemGatewayRoute route) {
        boolean result;
        Integer status = route.getStatus();
        route.setStatus(status == 0 ? 1 : 0);
        route.setModifier(userId);
        route.setModifyTime(LocalDateTime.now());
        result = this.updateById(route);
        GatewayRouteChangeMessage message = new GatewayRouteChangeMessage();
        message.setRouteId(route.getId());
        message.setReceiver(userId);
        message.setDeliver(CommonConstant.SYSTEM_USER_ID);
        String operateType = "shutDown";
        if (status == 0 && result) {
            operateType = "turnOn";
        }
        source.output().send(MessageBuilder.withPayload(message).setHeader("operate-type", operateType).build());
        return result;
    }

    @Override
    public Boolean check(String name) {
        return ObjectUtil.isNotNull(this.getOne(Wrappers.<SystemGatewayRoute>lambdaQuery().eq(SystemGatewayRoute::getName, name)));
    }


}
