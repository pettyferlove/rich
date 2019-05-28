package com.github.rich.base.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.rich.base.entity.SystemDictType;
import com.github.rich.base.service.ISystemDictTypeService;
import com.github.rich.common.core.model.R;
import io.swagger.annotations.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author Petty
 */
@Api(value = "字典类型信息", tags = {"字典类型信息接口"})
@RestController
@RequestMapping("/dict/type")
public class DictTypeController {

    private final ISystemDictTypeService systemDictTypeService;

    public DictTypeController(ISystemDictTypeService systemDictTypeService) {
        this.systemDictTypeService = systemDictTypeService;
    }

    @ApiOperation(value = "获取字典类型列表", notes = "无需权限")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "object", name = "dictType", value = "DictType", dataTypeClass = SystemDictType.class),
            @ApiImplicitParam(paramType = "object", name = "page", value = "Page", dataTypeClass = Page.class)
    })
    @GetMapping("page")
    public R<IPage> page(SystemDictType systemDictType, Page<SystemDictType> page) {
        return new R<>(systemDictTypeService.page(systemDictType,page));
    }

    @ApiOperation(value = "获取字典类型详情", notes = "无需权限")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "code", value = "Code", dataTypeClass = String.class)
    })
    @GetMapping("/{code}")
    public R<SystemDictType> get(@PathVariable String code){
        return new R<>(systemDictTypeService.get(code));
    }

    @ApiOperation(value = "创建字典类型", notes = "需要管理员权限",authorizations = @Authorization(value = "oauth2"))
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "object", name = "dictType", value = "DictType", dataTypeClass = SystemDictType.class)
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public R<String> create(SystemDictType systemDictType){
        return new R<>(systemDictTypeService.create(systemDictType));
    }

    @ApiOperation(value = "更新字典类型", notes = "需要管理员权限",authorizations = @Authorization(value = "oauth2"))
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "object", name = "dictType", value = "DictType", dataTypeClass = SystemDictType.class)
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping
    public R<Boolean> update(SystemDictType systemDictType){
        return new R<>(systemDictTypeService.update(systemDictType));
    }

    @ApiOperation(value = "删除字典类型", notes = "需要管理员权限",authorizations = @Authorization(value = "oauth2"))
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "code", value = "Code", dataTypeClass = String.class)
    })
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{code}")
    public R<Integer> delete(@PathVariable String code){
        return new R<>(systemDictTypeService.delete(code));
    }
}
