package com.github.rich.base.controller;

import com.github.rich.base.entity.SystemMenuResource;
import com.github.rich.base.service.ISystemMenuResourceService;
import com.github.rich.base.vo.MenuNode;
import com.github.rich.common.core.model.R;
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

    /**
     * TODO 根据用户获取菜单 待开发
     * @return null
     */
    @GetMapping
    public R<MenuNode> menu(){
        return null;
    }

    @ApiOperation(value = "创建菜单节点", notes = "需要管理员权限",authorizations = @Authorization(value = "oauth2"))
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "object", name = "menu", value = "Menu", dataTypeClass = SystemMenuResource.class)
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/node/create")
    public R<String> createNode(SystemMenuResource menu) {
        return new R<>(systemMenuResourceService.createNode(menu));
    }

    @ApiOperation(value = "加载菜单树", notes = "无需权限")
    @GetMapping("/tree")
    public R<List<MenuNode>> loadTree(){
        return new R<>(systemMenuResourceService.loadTree());
    }

    @ApiOperation(value = "获取菜单节点详情", notes = "无需权限")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "code", value = "Code", dataTypeClass = String.class)
    })
    @GetMapping("/node/{code}")
    public R<MenuNode> getNode(@PathVariable String code){
        return new R<>(systemMenuResourceService.getNode(code));
    }

    @ApiOperation(value = "删除菜单节点", notes = "如果有子节点则删除失败，需要管理员权限",authorizations = @Authorization(value = "oauth2"))
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "code", value = "Code", dataTypeClass = String.class)
    })
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/node/{code}")
    public R<Boolean> deleteNode(@PathVariable String code) throws Exception {
        return new R<>(systemMenuResourceService.deleteNode(code));
    }

    @ApiOperation(value = "更新菜单节点", notes = "需要管理员权限",authorizations = @Authorization(value = "oauth2"))
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "object", name = "menu", value = "Menu", dataTypeClass = SystemMenuResource.class)
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/node")
    public R<Boolean> updateNode(SystemMenuResource menu){
        return new R<>(systemMenuResourceService.updateNode(menu));
    }

    @ApiOperation(value = "获取当前节点的子节点列表", notes = "无需权限")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "parentCode", value = "ParentCode", dataTypeClass = String.class)
    })
    @GetMapping("children/nodes/{parentCode}")
    public R<List> getChildrenNodes(@PathVariable String parentCode){
        return new R<>(systemMenuResourceService.loadChildrenNodes(parentCode));
    }

}
