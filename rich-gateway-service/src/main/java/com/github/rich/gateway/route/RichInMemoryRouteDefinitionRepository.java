package com.github.rich.gateway.route;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * RouteDefinition储存
 * 实现一个默认实例，否则会注入Spring默认InMemoryRouteDefinitionRepository
 *
 * @author Petty
 * @see org.springframework.cloud.gateway.route.InMemoryRouteDefinitionRepository
 */
@Slf4j
@Component
public class RichInMemoryRouteDefinitionRepository implements RouteDefinitionRepository {

    private final Map<String, RouteDefinition> routes = new ConcurrentHashMap<>();

    /**
     * 获取Route定义信息（核心）
     *
     * @return Flux
     */
    @Override
    public Flux<RouteDefinition> getRouteDefinitions() {
        return Flux.fromIterable(routes.values());
    }

    /**
     * 保存RouteDefinition
     *
     * @param route RouteDefinition
     * @return Mono
     */
    @Override
    public Mono<Void> save(Mono<RouteDefinition> route) {
        return route.flatMap(r -> {
            routes.put(r.getId(), r);
            return Mono.empty();
        });
    }

    /**
     * 移除RouteDefinition
     *
     * @param routeId RouteID
     * @return Mono
     */
    @Override
    public Mono<Void> delete(Mono<String> routeId) {
        return routeId.flatMap(id -> {
            if (routes.containsKey(id)) {
                routes.remove(id);
                return Mono.empty();
            }
            log.error("RouteDefinition no found routeId:{}", routeId);
            return Mono.defer(() -> Mono.error(new NotFoundException("RouteDefinition not found: " + routeId)));
        });
    }
}
