package com.github.rich.base.controller;


import com.github.rich.base.entity.SystemRegion;
import com.github.rich.base.service.ISystemRegionService;
import com.github.rich.base.domain.vo.RegionNode;
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
 * <p>
 * 行政区划信息表 接口控制器
 * </p>
 *
 * @author Petty
 * @since 2019-06-26
 */
@Api(value = "行政区划信息表", tags = {"行政区划信息表接口"})
@RestController
@RequestMapping("/region")
public class RegionController {
    private final ISystemRegionService systemRegionService;

    public RegionController(ISystemRegionService systemRegionService) {
        this.systemRegionService = systemRegionService;
    }

    @ApiOperation(value = "加载行政区划树", notes = "无需特殊权限", authorizations = @Authorization(value = "oauth2"))
    @GetMapping("/tree")
    public R<List<RegionNode>> loadTree() {
        return new R<>(systemRegionService.loadTree());
    }

    @ApiOperation(value = "加载行政区划节点列表", notes = "无需特殊权限", authorizations = @Authorization(value = "oauth2"))
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "id", value = "id", dataTypeClass = String.class)
    })
    @GetMapping("/nodes/{id}")
    public R<List<SystemRegion>> loadNodes(@PathVariable String id) {
        return new R<>(systemRegionService.loadNodes(id));
    }

    @ApiOperation(value = "获取行政区划信息表详情", notes = "无需特殊权限", authorizations = @Authorization(value = "oauth2"))
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "id", value = "id", dataTypeClass = String.class)
    })
    @GetMapping("/{id}")
    public R<SystemRegion> get(@PathVariable String id) {
        return new R<>(systemRegionService.get(id));
    }

    @ApiOperation(value = "创建行政区划信息表", notes = "无需特殊权限", authorizations = @Authorization(value = "oauth2"))
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "systemRegion", value = "systemRegion", dataTypeClass = SystemRegion.class)
    })
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @PostMapping
    @UserLog(type = OperateType.ADD, description = "创建行政区划信息表")
    public R<String> create(SystemRegion systemRegion) {
        return new R<>(systemRegionService.create(Objects.requireNonNull(SecurityUtil.getUser()).getUserId(), systemRegion));
    }

    @ApiOperation(value = "更新行政区划信息表", notes = "无需特殊权限", authorizations = @Authorization(value = "oauth2"))
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "systemRegion", value = "systemRegion", dataTypeClass = SystemRegion.class)
    })
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @PutMapping
    @UserLog(type = OperateType.UPDATE, description = "更新行政区划信息表")
    public R<Boolean> update(SystemRegion systemRegion) {
        return new R<>(systemRegionService.update(Objects.requireNonNull(SecurityUtil.getUser()).getUserId(), systemRegion));
    }

    @ApiOperation(value = "删除行政区划信息表", notes = "无需特殊权限", authorizations = @Authorization(value = "oauth2"))
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "id", value = "id", dataTypeClass = String.class)
    })
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @DeleteMapping("/{id}")
    @UserLog(type = OperateType.DELETE, description = "删除行政区划信息表")
    public R<Boolean> delete(@PathVariable String id) {
        return new R<>(systemRegionService.delete(id));
    }

    @ApiOperation(value = "检测区域编码是否存在", notes = "无需特殊权限", authorizations = @Authorization(value = "oauth2"))
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "region", value = "region", dataTypeClass = String.class)
    })
    @GetMapping("/check/{region}")
    public R<Boolean> check(@PathVariable String region) {
        return new R<>(systemRegionService.check(region));
    }

}
