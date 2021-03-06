package com.github.rich.base.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.rich.base.entity.SystemRole;
import com.github.rich.base.service.ISystemRoleService;
import com.github.rich.common.core.domain.vo.R;
import com.github.rich.log.annotation.UserLog;
import com.github.rich.log.constants.OperateType;
import com.github.rich.security.utils.SecurityUtil;
import io.swagger.annotations.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

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
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN')")
    @PostMapping
    @UserLog(type = OperateType.ADD, description = "创建角色")
    public R<String> create(SystemRole systemRole) {
        return new R<>(systemRoleService.create(Objects.requireNonNull(SecurityUtil.getUser()).getUserId(), systemRole));
    }

    @ApiOperation(value = "更新角色", notes = "需要管理员权限", authorizations = @Authorization(value = "oauth2"))
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "systemRole", value = "SystemRole", dataTypeClass = SystemRole.class)
    })
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN')")
    @PutMapping
    @UserLog(type = OperateType.UPDATE, description = "更新角色")
    public R<Boolean> update(SystemRole systemRole) {
        return new R<>(systemRoleService.update(Objects.requireNonNull(SecurityUtil.getUser()).getUserId(), systemRole));
    }

    @ApiOperation(value = "删除角色", notes = "删除角色将会删除与角色相关的关联数据，同时需要管理员权限", authorizations = @Authorization(value = "oauth2"))
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "id", value = "id", dataTypeClass = String.class)
    })
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN')")
    @DeleteMapping("/{id}")
    @UserLog(type = OperateType.DELETE, description = "删除角色")
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

    @ApiOperation(value = "更新角色下已绑定资源（菜单）", notes = "需要管理员权限", authorizations = @Authorization(value = "oauth2"))
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "roleId", value = "roleId", dataTypeClass = String.class),
            @ApiImplicitParam(paramType = "query", name = "addIds", value = "addIds", dataTypeClass = String[].class),
            @ApiImplicitParam(paramType = "query", name = "removeIds", value = "removeIds", dataTypeClass = String[].class)
    })
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN')")
    @PutMapping("/authority")
    @UserLog(type = OperateType.UPDATE, description = "更新角色下已绑定资源（菜单）")
    public R<Boolean> authorityUpdate(String roleId, String[] addIds, String[] removeIds) {
        return new R<>(systemRoleService.updateMenuForRole(roleId, addIds, removeIds));
    }

    @ApiOperation(value = "获取系统全部角色", notes = "无需特殊权限", authorizations = @Authorization(value = "oauth2"))
    @GetMapping("/all")
    public R<List<SystemRole>> all() {
        return new R<>(systemRoleService.list());
    }

    @ApiOperation(value = "获取用户下角色ID", notes = "无需特殊权限", authorizations = @Authorization(value = "oauth2"))
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "userId", value = "userId", dataTypeClass = String.class)
    })
    @GetMapping("/user/{userId}")
    public R<List<String>> user(@PathVariable String userId) {
        return new R<>(systemRoleService.findRoleKeyByUserId(userId));
    }

    @ApiOperation(value = "为用户增加角色", notes = "需要管理员权限", authorizations = @Authorization(value = "oauth2"))
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userId", value = "userId", dataTypeClass = String.class),
            @ApiImplicitParam(paramType = "query", name = "ids", value = "ids", dataTypeClass = String[].class)
    })
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN')")
    @PostMapping("/user/add")
    @UserLog(type = OperateType.ADD, description = "为用户增加角色")
    public R<Boolean> userAdd(String userId, String[] ids) {
        return new R<>(systemRoleService.addUserRole(userId, ids));
    }

    @ApiOperation(value = "为用户删除角色", notes = "需要管理员权限", authorizations = @Authorization(value = "oauth2"))
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userId", value = "userId", dataTypeClass = String.class),
            @ApiImplicitParam(paramType = "query", name = "ids", value = "ids", dataTypeClass = String[].class)
    })
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN')")
    @PostMapping("/user/delete")
    @UserLog(type = OperateType.DELETE, description = "为用户删除角色")
    public R<Boolean> userDelete(String userId, String[] ids) {
        return new R<>(systemRoleService.deleteUserRole(userId, ids));
    }


    @ApiOperation(value = "检测角色是否存在", notes = "无需特殊权限", authorizations = @Authorization(value = "oauth2"))
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "role", value = "role", dataTypeClass = String.class)
    })
    @GetMapping("/check/{role}")
    public R<Boolean> check(@PathVariable String role) {
        return new R<>(systemRoleService.check(role));
    }

}
