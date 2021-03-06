package com.github.rich.base.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.rich.base.entity.SystemDictItem;
import com.github.rich.base.service.ISystemDictItemService;
import com.github.rich.common.core.domain.vo.R;
import com.github.rich.log.annotation.UserLog;
import com.github.rich.log.constants.OperateType;
import com.github.rich.security.utils.SecurityUtil;
import io.swagger.annotations.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Objects;

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

    @ApiOperation(value = "获取字典项列表", notes = "无需特殊权限", authorizations = @Authorization(value = "oauth2"))
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "page", value = "Page", dataTypeClass = Page.class),
            @ApiImplicitParam(paramType = "query", name = "typeId", value = "TypeId", dataTypeClass = String.class)
    })
    @GetMapping("page")
    public R<IPage> page(String typeId, Page<SystemDictItem> page) {
        return new R<>(systemDictItemService.page(typeId, page));
    }

    @ApiOperation(value = "获取字典项详情", notes = "无需特殊权限", authorizations = @Authorization(value = "oauth2"))
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "id", value = "id", dataTypeClass = String.class)
    })
    @GetMapping("/{id}")
    public R<SystemDictItem> get(@PathVariable String id) {
        return new R<>(systemDictItemService.get(id));
    }

    @ApiOperation(value = "创建字典项", notes = "需要管理员权限", authorizations = @Authorization(value = "oauth2"))
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "dictItem", value = "DictItem", dataTypeClass = SystemDictItem.class)
    })
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @PostMapping
    @UserLog(type = OperateType.ADD, description = "创建字典项")
    public R<String> create(SystemDictItem systemDictItem) {
        return new R<>(systemDictItemService.create(Objects.requireNonNull(SecurityUtil.getUser()).getUserId(), systemDictItem));
    }

    @ApiOperation(value = "更新字典项", notes = "需要管理员权限", authorizations = @Authorization(value = "oauth2"))
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "dictItem", value = "DictItem", dataTypeClass = SystemDictItem.class)
    })
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @PutMapping
    @UserLog(type = OperateType.UPDATE, description = "更新字典项")
    public R<Boolean> update(SystemDictItem systemDictItem) {
        return new R<>(systemDictItemService.update(Objects.requireNonNull(SecurityUtil.getUser()).getUserId(), systemDictItem));
    }

    @ApiOperation(value = "删除字典项", notes = "需要管理员权限", authorizations = @Authorization(value = "oauth2"))
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "id", value = "id", dataTypeClass = String.class)
    })
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @DeleteMapping("/{id}")
    @UserLog(type = OperateType.DELETE, description = "删除字典项")
    public R<Boolean> delete(@PathVariable String id) {
        return new R<>(systemDictItemService.delete(id));
    }

    @ApiOperation(value = "批量删除字典项", notes = "需要管理员权限", authorizations = @Authorization(value = "oauth2"))
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "ids", value = "ids", dataTypeClass = String[].class)
    })
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @PostMapping("/delete/batch")
    @Deprecated
    @UserLog(type = OperateType.DELETE, description = "批量删除字典项")
    public R<Boolean> deleteByCodes(String[] ids) {
        return new R<>(systemDictItemService.deleteByCodes(Arrays.asList(ids)));
    }
}
