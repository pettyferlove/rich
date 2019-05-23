package com.github.rich.base.controller;

import com.github.rich.base.entity.SystemMenuResource;
import com.github.rich.base.service.ISystemMenuResourceService;
import com.github.rich.common.core.model.R;
import io.swagger.annotations.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Petty
 */
@Api(value = "菜单信息", tags = {"菜单信息接口"})
@RestController
@RequestMapping("/menu")
public class MenuController {

    private final ISystemMenuResourceService systemMenuResourceService;

    public MenuController(ISystemMenuResourceService systemMenuResourceService) {
        this.systemMenuResourceService = systemMenuResourceService;
    }

    @ApiOperation(value = "创建菜单", notes = "需要管理员权限",authorizations = @Authorization(value = "oauth2"))
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "object", name = "menu", value = "Menu", dataTypeClass = SystemMenuResource.class)
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/node/create")
    public R<String> createNode(SystemMenuResource menu) {
        return new R<>(systemMenuResourceService.createNode(menu));
    }

}
