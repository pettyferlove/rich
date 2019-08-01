package com.github.rich.base.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.rich.common.core.vo.R;
import com.github.rich.log.annotation.UserLog;
import org.springframework.web.bind.annotation.*;
import com.github.rich.log.constants.OperateType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;
import com.github.rich.base.service.ISystemTenantService;
import com.github.rich.base.entity.SystemTenant;
import io.swagger.annotations.*;

import java.util.List;

/**
 * <p>
 * 租户信息 接口控制器
 * </p>
 *
 * @author Petty
 * @since 2019-07-13
 */
@Api(value = "租户信息", tags = {"租户信息接口"})
@RestController
@RequestMapping("/tenant")
public class TenantController {
    private final ISystemTenantService systemTenantService;

    public TenantController(ISystemTenantService systemTenantService) {
        this.systemTenantService = systemTenantService;
    }

    @ApiOperation(value = "获取租户信息列表", notes = "无需特殊权限", authorizations = @Authorization(value = "oauth2"))
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "systemTenant", value = "systemTenant", dataTypeClass = SystemTenant.class),
            @ApiImplicitParam(paramType = "query", name = "page", value = "Page", dataTypeClass = Page.class)
    })
    @GetMapping("page")
    public R<IPage> page(SystemTenant systemTenant, Page<SystemTenant> page) {
        return new R<>(systemTenantService.page(systemTenant, page));
    }

    @ApiOperation(value = "获取全部租户信息表", notes = "无需特殊权限", authorizations = @Authorization(value = "oauth2"))
    @GetMapping("all")
    public R<List> all() {
        return new R<>(systemTenantService.all());
    }


    @ApiOperation(value = "获取租户信息详情", notes = "无需特殊权限", authorizations = @Authorization(value = "oauth2"))
            @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "id", value = "id", dataTypeClass = String.class)
    })
    @GetMapping("/{id}")
    public R<SystemTenant> get(@PathVariable String id) {
        return new R<>(systemTenantService.get(id));
    }

    @ApiOperation(value = "创建租户信息", notes = "无需特殊权限", authorizations = @Authorization(value = "oauth2"))
            @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "systemTenant", value = "systemTenant", dataTypeClass = SystemTenant.class)
    })
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @PostMapping
    @UserLog(type = OperateType.ADD, description = "创建租户信息")
    public R<String> create(SystemTenant systemTenant) {
        return new R<>(systemTenantService.create(systemTenant));
    }

    @ApiOperation(value = "更新租户信息", notes = "无需特殊权限", authorizations = @Authorization(value = "oauth2"))
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "systemTenant", value = "systemTenant", dataTypeClass = SystemTenant.class)
    })
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @PutMapping
    @UserLog(type = OperateType.UPDATE, description = "更新租户信息")
    public R<Boolean> update(SystemTenant systemTenant) {
        return new R<>(systemTenantService.update(systemTenant));
    }

    @ApiOperation(value = "删除租户信息", notes = "无需特殊权限", authorizations = @Authorization(value = "oauth2"))
    @ApiImplicitParams({
    @ApiImplicitParam(paramType = "path", name = "id", value = "id", dataTypeClass = String.class)
    })
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @DeleteMapping("/{id}")
    @UserLog(type = OperateType.DELETE, description = "删除租户信息")
    public R<Boolean> delete(@PathVariable String id) {
        return new R<>(systemTenantService.delete(id));
    }


    @ApiOperation(value = "更新租户状态", notes = "需要管理员权限", authorizations = @Authorization(value = "oauth2"))
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "tenant", value = "tenant", dataTypeClass = SystemTenant.class)
    })
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @PutMapping(value = "/status")
    @UserLog(type = OperateType.UPDATE, description = "更新路由状态")
    public R<Boolean> changeStatus(SystemTenant tenant) {
        return new R<>(systemTenantService.changeStatus(tenant));
    }

    @ApiOperation(value = "检测租户是否存在", notes = "无需特殊权限", authorizations = @Authorization(value = "oauth2"))
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "name", value = "name", dataTypeClass = String.class)
    })
    @GetMapping("/check/{tenantId}")
    public R<Boolean> check(@PathVariable String tenantId) {
        return new R<>(systemTenantService.check(tenantId));
    }

}
