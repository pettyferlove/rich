package com.github.rich.base.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.rich.base.entity.SystemDictItem;
import com.github.rich.base.service.ISystemDictItemService;
import com.github.rich.common.core.model.R;
import io.swagger.annotations.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Petty
 */
@Api(value = "字典项信息", tags = {"字典项信息接口"})
@RestController
@RequestMapping("/dict/item")
public class DictItemController {

    private final ISystemDictItemService systemDictItemService;

    public DictItemController(ISystemDictItemService systemDictItemService) {
        this.systemDictItemService = systemDictItemService;
    }

    @ApiOperation(value = "获取字典项列表", notes = "无需权限")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "object", name = "page", value = "Page", dataTypeClass = Page.class),
            @ApiImplicitParam(paramType = "query", name = "typeCode", value = "TypeCode", dataTypeClass = String.class)
    })
    @GetMapping("page")
    public R<IPage> page(String typeCode, Page<SystemDictItem> page) {
        return new R<>(systemDictItemService.page(typeCode, page));
    }

    @ApiOperation(value = "获取字典项详情", notes = "无需权限")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "code", value = "Code", dataTypeClass = String.class)
    })
    @GetMapping("/{code}")
    public R<SystemDictItem> get(@PathVariable String code){
        return new R<>(systemDictItemService.get(code));
    }

    @ApiOperation(value = "创建字典项", notes = "需要管理员权限",authorizations = @Authorization(value = "oauth2"))
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "object", name = "dictItem", value = "DictItem", dataTypeClass = SystemDictItem.class)
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public R<String> create(SystemDictItem systemDictItem){
        return new R<>(systemDictItemService.create(systemDictItem));
    }

    @ApiOperation(value = "更新字典项", notes = "需要管理员权限",authorizations = @Authorization(value = "oauth2"))
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "object", name = "dictItem", value = "DictItem", dataTypeClass = SystemDictItem.class)
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping
    public R<Boolean> update(SystemDictItem systemDictItem){
        return new R<>(systemDictItemService.update(systemDictItem));
    }

    @ApiOperation(value = "删除字典项", notes = "需要管理员权限",authorizations = @Authorization(value = "oauth2"))
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "code", value = "Code", dataTypeClass = String.class)
    })
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{code}")
    public R<Boolean> delete(@PathVariable String code){
        return new R<>(systemDictItemService.delete(code));
    }

    @ApiOperation(value = "批量删除字典项", notes = "需要管理员权限",authorizations = @Authorization(value = "oauth2"))
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "codes", value = "Codes", dataTypeClass = List.class)
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/delete/codes")
    @Deprecated
    public R<Boolean> deleteByCodes(List<String> codes){
        return new R<>(systemDictItemService.deleteByCodes(codes));
    }
}
