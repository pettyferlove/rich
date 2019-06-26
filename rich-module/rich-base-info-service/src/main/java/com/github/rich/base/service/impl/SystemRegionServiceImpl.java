package com.github.rich.base.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.rich.base.entity.SystemRegion;
import com.github.rich.base.mapper.SystemRegionMapper;
import com.github.rich.base.service.ISystemRegionService;
import com.github.rich.base.utils.TreeUtils;
import com.github.rich.base.vo.RegionNode;
import com.github.rich.common.core.exception.BaseRuntimeException;
import com.github.rich.common.core.utils.ConverterUtil;
import com.github.rich.security.utils.SecurityUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * <p>
 * 行政区划信息表 服务实现类
 * </p>
 *
 * @author Petty
 * @since 2019-06-26
 */
@Service
public class SystemRegionServiceImpl extends ServiceImpl<SystemRegionMapper, SystemRegion> implements ISystemRegionService {

    @Override
    public List<RegionNode> loadTree() {
        List<SystemRegion> systemMenuResources = this.list(Wrappers.<SystemRegion>lambdaQuery().orderByAsc(SystemRegion::getId));
        return TreeUtils.buildTree(Optional.ofNullable(ConverterUtil.convertList(SystemRegion.class, RegionNode.class, systemMenuResources)).orElseGet(ArrayList::new), "86");
    }

    @Override
    public List<SystemRegion> loadNodes(String id) {
        return this.list(Wrappers.<SystemRegion>lambdaQuery().eq(SystemRegion::getParentId, id));
    }

    @Override
    public SystemRegion get(String id) {
        return this.getById(id);
    }

    @Override
    public Boolean delete(String id) {
        List<SystemRegion> systemMenuResources = this.list(Wrappers.<SystemRegion>lambdaQuery().eq(SystemRegion::getParentId, id));
        if (!systemMenuResources.isEmpty()) {
            throw new BaseRuntimeException("存在子区划，无法删除");
        }
        return this.removeById(id);
    }

    @Override
    public String create(SystemRegion systemRegion) {
        systemRegion.setCreator(Objects.requireNonNull(SecurityUtil.getUser()).getUserId());
        systemRegion.setCreateTime(LocalDateTime.now());
        if (this.save(systemRegion)) {
            return systemRegion.getId();
        } else {
            throw new BaseRuntimeException("新增失败");
        }
    }

    @Override
    public Boolean update(SystemRegion systemRegion) {
        systemRegion.setModifier(Objects.requireNonNull(SecurityUtil.getUser()).getUserId());
        systemRegion.setModifierTime(LocalDateTime.now());
        return this.updateById(systemRegion);
    }

}
