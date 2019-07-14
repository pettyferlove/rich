package com.github.rich.base.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.rich.base.entity.SystemGatewayRoute;
import com.github.rich.base.service.ISystemGatewayRouteService;
import com.github.rich.common.core.vo.R;
import com.github.rich.log.annotation.UserLog;
import com.github.rich.log.constants.OperateType;
import io.swagger.annotations.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author Petty
 */
@Api(value = "Gateway路由信息", tags = {"Gateway路由信息接口"})
@RestController
@RequestMapping("/route")
public class GatewayRouteController {

    private final ISystemGatewayRouteService systemGatewayRouteService;

    public GatewayRouteController(ISystemGatewayRouteService systemGatewayRouteService) {
        this.systemGatewayRouteService = systemGatewayRouteService;
    }

    @ApiOperation(value = "获取路由信息列表", notes = "无需特殊权限", authorizations = @Authorization(value = "oauth2"))
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "gatewayRoute", value = "GatewayRoute", dataTypeClass = SystemGatewayRoute.class),
            @ApiImplicitParam(paramType = "query", name = "page", value = "Page", dataTypeClass = Page.class)
    })
    @GetMapping("page")
    public R<IPage> page(SystemGatewayRoute systemGatewayRoute, Page<SystemGatewayRoute> page) {
        return new R<>(systemGatewayRouteService.page(systemGatewayRoute, page));
    }

    @ApiOperation(value = "获取路由信息详情", notes = "无需特殊权限", authorizations = @Authorization(value = "oauth2"))
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "id", value = "id", dataTypeClass = String.class)
    })
    @GetMapping("/{id}")
    public R<SystemGatewayRoute> get(@PathVariable String id) {
        return new R<>(systemGatewayRouteService.get(id));
    }

    @ApiOperation(value = "创建路由", notes = "需要管理员权限", authorizations = @Authorization(value = "oauth2"))
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "gatewayRoute", value = "GatewayRoute", dataTypeClass = SystemGatewayRoute.class)
    })
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @PostMapping
    @UserLog(type = OperateType.ADD, description = "创建路由")
    public R<String> create(SystemGatewayRoute gatewayRoute) {
        return new R<>(systemGatewayRouteService.create(gatewayRoute));
    }

    @ApiOperation(value = "更新路由", notes = "需要管理员权限", authorizations = @Authorization(value = "oauth2"))
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "gatewayRoute", value = "GatewayRoute", dataTypeClass = SystemGatewayRoute.class)
    })
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @PutMapping
    @UserLog(type = OperateType.UPDATE, description = "更新路由")
    public R<Boolean> update(SystemGatewayRoute gatewayRoute) {
        return new R<>(systemGatewayRouteService.update(gatewayRoute));
    }

    @ApiOperation(value = "删除路由", notes = "需要管理员权限", authorizations = @Authorization(value = "oauth2"))
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "id", value = "id", dataTypeClass = String.class)
    })
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @DeleteMapping("/{id}")
    @UserLog(type = OperateType.DELETE, description = "删除路由")
    public R<Boolean> delete(@PathVariable String id) {
        return new R<>(systemGatewayRouteService.delete(id));
    }

    @ApiOperation(value = "更新路由状态", notes = "需要管理员权限", authorizations = @Authorization(value = "oauth2"))
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "gatewayRoute", value = "GatewayRoute", dataTypeClass = SystemGatewayRoute.class)
    })
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @PutMapping(value = "/status")
    @UserLog(type = OperateType.UPDATE, description = "更新路由状态")
    public R<Boolean> changeStatus(SystemGatewayRoute gatewayRoute) {
        return new R<>(systemGatewayRouteService.changeStatus(gatewayRoute));
    }

    @ApiOperation(value = "检测路由是否存在", notes = "无需特殊权限", authorizations = @Authorization(value = "oauth2"))
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "name", value = "name", dataTypeClass = String.class)
    })
    @GetMapping("/check/{name}")
    public R<Boolean> check(@PathVariable String name) {
        return new R<>(systemGatewayRouteService.check(name));
    }

}
