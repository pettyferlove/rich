package com.github.rich.base.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.rich.base.entity.SystemRole;
import com.github.rich.base.entity.SystemUser;
import com.github.rich.base.service.ISystemUserService;
import com.github.rich.base.vo.UserInfoVO;
import com.github.rich.common.core.model.R;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author Petty
 */
@Api(value = "用户信息", tags = {"用户信息接口"})
@RestController
@RequestMapping("/user")
public class UserController {

    private final ISystemUserService systemUserService;

    public UserController(ISystemUserService systemUserService) {
        this.systemUserService = systemUserService;
    }

    /**
     * 通过OpenID UnionID快速注册
     *
     * @param openid  微信OpenID
     * @param unionid 微信UnionID
     * @return 是否成功 True 是 False 否
     */
    @ApiOperation(value = "通过OpenID快速注册用户信息", notes = "无需权限")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", name = "openid", value = "OpenID", dataTypeClass = String.class),
            @ApiImplicitParam(paramType = "form", name = "unionid", value = "UnionID", dataTypeClass = String.class)
    })
    @PostMapping("/register/wechat/open")
    public R<Boolean> registerByWeChatOpenID(String openid, String unionid) {
        openid = StringUtils.isBlank(openid) ? "" : openid;
        unionid = StringUtils.isBlank(unionid) ? "" : unionid;
        return new R<>(systemUserService.registerByWeChatOpenID(openid, unionid));
    }

    @ApiOperation(value = "获取用户列表", notes = "无需特殊权限", authorizations = @Authorization(value = "oauth2"))
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "systemUser", value = "SystemUser", dataTypeClass = SystemUser.class),
            @ApiImplicitParam(paramType = "query", name = "page", value = "Page", dataTypeClass = Page.class)
    })
    @GetMapping("page")
    public R<IPage> page(SystemUser systemUser, Page<SystemUser> page) {
        return new R<>(systemUserService.page(systemUser, page));
    }

    @ApiOperation(value = "获取用户信息", notes = "无需特殊权限", authorizations = @Authorization(value = "oauth2"))
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "id", value = "id", dataTypeClass = String.class)
    })
    @GetMapping("/{id}")
    public R<SystemUser> get(@PathVariable String id) {
        return new R<>(systemUserService.get(id));
    }

    @ApiOperation(value = "创建用户", notes = "需要管理员权限", authorizations = @Authorization(value = "oauth2"))
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "systemUser", value = "SystemUser", dataTypeClass = SystemUser.class)
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public R<String> create(SystemUser systemUser) {
        return new R<>(systemUserService.create(systemUser));
    }

    @ApiOperation(value = "更新用户", notes = "需要管理员权限", authorizations = @Authorization(value = "oauth2"))
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "systemUser", value = "SystemUser", dataTypeClass = SystemUser.class)
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping
    public R<Boolean> update(SystemUser systemUser) {
        return new R<>(systemUserService.update(systemUser));
    }

    @ApiOperation(value = "删除用户", notes = "删除用户将会删除与用户相关的关联数据，同时需要管理员权限", authorizations = @Authorization(value = "oauth2"))
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "id", value = "id", dataTypeClass = String.class)
    })
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public R<Boolean> delete(@PathVariable String id) {
        return new R<>(systemUserService.delete(id));
    }

    @ApiOperation(value = "获取用户详情", notes = "无需特殊权限", authorizations = @Authorization(value = "oauth2"))
    @GetMapping("/info")
    public R<UserInfoVO> info() {
        return new R<>(systemUserService.getUserInfo());
    }

}
