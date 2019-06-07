package com.github.rich.base.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.rich.base.entity.SystemRole;
import com.github.rich.base.service.ISystemRoleService;
import com.github.rich.common.core.model.R;
import io.swagger.annotations.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Petty
 */
@Api(value = "角色信息", tags = {"角色信息接口"})
@RestController
@RequestMapping("/role")
public class RoleController {

    private final ISystemRoleService systemRoleService;

    public RoleController(ISystemRoleService systemRoleService) {
        this.systemRoleService = systemRoleService;
    }

    @ApiOperation(value = "获取角色列表", notes = "无需特殊权限", authorizations = @Authorization(value = "oauth2"))
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "systemRole", value = "SystemRole", dataTypeClass = SystemRole.class),
            @ApiImplicitParam(paramType = "query", name = "page", value = "Page", dataTypeClass = Page.class)
    })
    @GetMapping("page")
    public R<IPage> page(SystemRole systemRole, Page<SystemRole> page) {
        return new R<>(systemRoleService.page(systemRole, page));
    }

    @ApiOperation(value = "获取角色详情", notes = "无需特殊权限", authorizations = @Authorization(value = "oauth2"))
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "id", value = "id", dataTypeClass = String.class)
    })
    @GetMapping("/{id}")
    public R<SystemRole> get(@PathVariable String id) {
        return new R<>(systemRoleService.get(id));
    }

    @ApiOperation(value = "创建角色", notes = "需要管理员权限", authorizations = @Authorization(value = "oauth2"))
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "systemRole", value = "SystemRole", dataTypeClass = SystemRole.class)
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public R<String> create(SystemRole systemRole) {
        return new R<>(systemRoleService.create(systemRole));
    }

    @ApiOperation(value = "更新角色", notes = "需要管理员权限", authorizations = @Authorization(value = "oauth2"))
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "systemRole", value = "SystemRole", dataTypeClass = SystemRole.class)
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping
    public R<Boolean> update(SystemRole systemRole) {
        return new R<>(systemRoleService.update(systemRole));
    }

    @ApiOperation(value = "删除角色", notes = "删除角色将会删除与角色相关的关联数据，同时需要管理员权限", authorizations = @Authorization(value = "oauth2"))
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "id", value = "id", dataTypeClass = String.class)
    })
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public R<Boolean> delete(@PathVariable String id) {
        return new R<>(systemRoleService.delete(id));
    }


    @ApiOperation(value = "获取角色下已绑定资源（菜单）", notes = "无需特殊权限", authorizations = @Authorization(value = "oauth2"))
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "roleId", value = "roleId", dataTypeClass = String.class)
    })
    @GetMapping("/authority/keys/{roleId}")
    public R<List<String>> authorityKey(@PathVariable String roleId) {
        return new R<>(systemRoleService.loadMenuKeysForRole(roleId));
    }

}
