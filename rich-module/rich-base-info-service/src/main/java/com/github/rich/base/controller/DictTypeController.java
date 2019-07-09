package com.github.rich.base.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.rich.base.entity.SystemDictType;
import com.github.rich.base.service.ISystemDictTypeService;
import com.github.rich.base.vo.Dict;
import com.github.rich.common.core.vo.R;
import com.github.rich.log.annotation.UserOperateLog;
import com.github.rich.log.constants.OperateType;
import io.swagger.annotations.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @ApiOperation(value = "获取字典类型列表", notes = "无需特殊权限", authorizations = @Authorization(value = "oauth2"))
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "dictType", value = "DictType", dataTypeClass = SystemDictType.class),
            @ApiImplicitParam(paramType = "query", name = "page", value = "Page", dataTypeClass = Page.class)
    })
    @GetMapping("page")
    public R<IPage> page(SystemDictType systemDictType, Page<SystemDictType> page) {
        return new R<>(systemDictTypeService.page(systemDictType, page));
    }

    @ApiOperation(value = "通过字典类型获取字典项列表", notes = "无需特殊权限", authorizations = @Authorization(value = "oauth2"))
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "type", value = "type", dataTypeClass = String.class)
    })
    @GetMapping("items/{type}")
    public R<List<Dict>> items(@PathVariable String type) {
        return new R<>(systemDictTypeService.list(type));
    }

    @ApiOperation(value = "获取字典类型详情", notes = "无需特殊权限", authorizations = @Authorization(value = "oauth2"))
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "id", value = "id", dataTypeClass = String.class)
    })
    @GetMapping("/{id}")
    public R<SystemDictType> get(@PathVariable String id) {
        return new R<>(systemDictTypeService.get(id));
    }

    @ApiOperation(value = "创建字典类型", notes = "需要管理员权限", authorizations = @Authorization(value = "oauth2"))
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "dictType", value = "DictType", dataTypeClass = SystemDictType.class)
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @UserOperateLog(type = OperateType.ADD, description = "创建字典类型")
    public R<String> create(SystemDictType systemDictType) {
        return new R<>(systemDictTypeService.create(systemDictType));
    }

    @ApiOperation(value = "更新字典类型", notes = "需要管理员权限", authorizations = @Authorization(value = "oauth2"))
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "dictType", value = "DictType", dataTypeClass = SystemDictType.class)
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping
    @UserOperateLog(type = OperateType.UPDATE, description = "更新字典类型")
    public R<Boolean> update(SystemDictType systemDictType) {
        return new R<>(systemDictTypeService.update(systemDictType));
    }

    @ApiOperation(value = "删除字典类型", notes = "需要管理员权限", authorizations = @Authorization(value = "oauth2"))
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "id", value = "id", dataTypeClass = String.class)
    })
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @UserOperateLog(type = OperateType.DELETE, description = "删除字典类型")
    public R<Integer> delete(@PathVariable String id) {
        return new R<>(systemDictTypeService.delete(id));
    }

    @ApiOperation(value = "检测字典类型是否存在", notes = "无需特殊权限", authorizations = @Authorization(value = "oauth2"))
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "region", value = "region", dataTypeClass = String.class)
    })
    @GetMapping("/check/{type}")
    public R<Boolean> check(@PathVariable String type) {
        return new R<>(systemDictTypeService.check(type));
    }

}
