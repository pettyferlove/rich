package com.github.rich.base.controller;

import com.github.rich.base.entity.SystemMenuResource;
import com.github.rich.base.service.ISystemMenuResourceService;
import com.github.rich.base.vo.MenuNode;
import com.github.rich.common.core.vo.R;
import com.github.rich.log.annotation.UserLog;
import com.github.rich.log.constants.OperateType;
import com.github.rich.security.utils.SecurityUtil;
import io.swagger.annotations.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @ApiOperation(value = "加载界面菜单数据", notes = "无需特殊权限", authorizations = @Authorization(value = "oauth2"))
    @GetMapping
    public R<List<MenuNode>> menu() {
        return new R<>(systemMenuResourceService.loadMenu(SecurityUtil.getUser()));
    }

    @ApiOperation(value = "创建菜单节点", notes = "需要管理员权限", authorizations = @Authorization(value = "oauth2"))
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "menu", value = "Menu", dataTypeClass = SystemMenuResource.class)
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/node/create")
    @UserLog(type = OperateType.ADD, description = "创建菜单节点")
    public R<String> createNode(SystemMenuResource menu) {
        return new R<>(systemMenuResourceService.createNode(menu));
    }

    @ApiOperation(value = "加载菜单树", notes = "无需特殊权限", authorizations = @Authorization(value = "oauth2"))
    @GetMapping("/tree")
    public R<List<MenuNode>> loadTree() {
        return new R<>(systemMenuResourceService.loadTree());
    }

    @ApiOperation(value = "获取菜单节点详情", notes = "无需特殊权限", authorizations = @Authorization(value = "oauth2"))
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "id", value = "id", dataTypeClass = String.class)
    })
    @GetMapping("/node/{id}")
    public R<MenuNode> getNode(@PathVariable String id) {
        return new R<>(systemMenuResourceService.getNode(id));
    }

    @ApiOperation(value = "删除菜单节点", notes = "如果有子节点则删除失败，需要管理员权限", authorizations = @Authorization(value = "oauth2"))
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "id", value = "id", dataTypeClass = String.class)
    })
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/node/{id}")
    @UserLog(type = OperateType.DELETE, description = "删除菜单节点")
    public R<Boolean> deleteNode(@PathVariable String id) throws Exception {
        return new R<>(systemMenuResourceService.deleteNode(id));
    }

    @ApiOperation(value = "更新菜单节点", notes = "需要管理员权限", authorizations = @Authorization(value = "oauth2"))
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "menu", value = "Menu", dataTypeClass = SystemMenuResource.class)
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/node")
    @UserLog(type = OperateType.UPDATE, description = "更新菜单节点")
    public R<Boolean> updateNode(SystemMenuResource menu) {
        return new R<>(systemMenuResourceService.updateNode(menu));
    }

    @ApiOperation(value = "获取当前节点的子节点列表", notes = "无需特殊权限", authorizations = @Authorization(value = "oauth2"))
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "parentId", value = "parentId", dataTypeClass = String.class)
    })
    @GetMapping("children/nodes/{parentId}")
    public R<List> getChildrenNodes(@PathVariable String parentId) {
        return new R<>(systemMenuResourceService.loadChildrenNodes(parentId));
    }

}
