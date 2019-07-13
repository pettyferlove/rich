package com.github.rich.log.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.rich.common.core.vo.R;
import com.github.rich.log.annotation.UserLog;
import com.github.rich.log.constants.OperateType;
import com.github.rich.log.service.IUserOperateLogService;
import com.github.rich.log.vo.UserLogVO;
import io.swagger.annotations.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author Petty
 */
@Api(value = "用户日志信息", tags = {"用户日志信息接口"})
@RestController
@RequestMapping("/user")
public class UserLogController {

    private final IUserOperateLogService userOperateLogService;

    public UserLogController(IUserOperateLogService userOperateLogService) {
        this.userOperateLogService = userOperateLogService;
    }

    @ApiOperation(value = "获取用户日志列表", notes = "无需特殊权限", authorizations = @Authorization(value = "oauth2"))
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "gatewayRoute", value = "GatewayRoute", dataTypeClass = com.github.rich.log.entity.UserOperateLog.class),
            @ApiImplicitParam(paramType = "query", name = "page", value = "Page", dataTypeClass = Page.class)
    })
    @GetMapping("page")
    public R<IPage> page(com.github.rich.log.entity.UserOperateLog systemGatewayRoute, Page<com.github.rich.log.entity.UserOperateLog> page) {
        return new R<>(userOperateLogService.page(systemGatewayRoute, page));
    }

    @ApiOperation(value = "获取用户日志详情", notes = "无需特殊权限", authorizations = @Authorization(value = "oauth2"))
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "id", value = "id", dataTypeClass = String.class)
    })
    @GetMapping("/{id}")
    public R<UserLogVO> get(@PathVariable String id) {
        return new R<>(userOperateLogService.get(id));
    }

    @ApiOperation(value = "删除用户日志", notes = "需要管理员权限", authorizations = @Authorization(value = "oauth2"))
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "id", value = "id", dataTypeClass = String.class)
    })
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @UserLog(type = OperateType.DELETE, description = "删除用户日志")
    public R<Boolean> delete(@PathVariable String id) {
        return new R<>(userOperateLogService.delete(id));
    }

}
