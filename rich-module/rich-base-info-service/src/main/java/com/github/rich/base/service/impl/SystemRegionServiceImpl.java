package com.github.rich.base.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.rich.base.constants.CacheConstant;
import com.github.rich.base.entity.SystemRegion;
import com.github.rich.base.mapper.SystemRegionMapper;
import com.github.rich.base.service.ISystemRegionService;
import com.github.rich.base.utils.TreeUtils;
import com.github.rich.base.domain.vo.RegionNode;
import com.github.rich.common.core.exception.BaseRuntimeException;
import com.github.rich.common.core.utils.ConverterUtil;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
    @Cacheable(value = CacheConstant.OUTER_API_PREFIX + "base-region-tree")
    public List<RegionNode> loadTree() {
        List<SystemRegion> systemMenuResources = this.list(Wrappers.<SystemRegion>lambdaQuery().orderByAsc(SystemRegion::getId));
        return TreeUtils.buildTree(Optional.ofNullable(ConverterUtil.convertList(SystemRegion.class, RegionNode.class, systemMenuResources)).orElseGet(ArrayList::new), "86");
    }

    @Override
    @Cacheable(value = CacheConstant.OUTER_API_PREFIX + "base-region-nodes", key = "#id", condition = "#id!=null")
    public List<SystemRegion> loadNodes(String id) {
        return this.list(Wrappers.<SystemRegion>lambdaQuery().eq(SystemRegion::getParentId, id));
    }

    @Override
    @Cacheable(value = CacheConstant.OUTER_API_PREFIX + "base-region-detail", key = "#id", condition = "#id!=null")
    public SystemRegion get(String id) {
        return this.getById(id);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = CacheConstant.OUTER_API_PREFIX + "base-region-detail", key = "#id", condition = "#id!=null"),
            @CacheEvict(value = CacheConstant.OUTER_API_PREFIX + "base-region-tree", allEntries = true),
            @CacheEvict(value = CacheConstant.OUTER_API_PREFIX + "base-region-nodes", allEntries = true)
    })
    public Boolean delete(String id) {
        List<SystemRegion> systemMenuResources = this.list(Wrappers.<SystemRegion>lambdaQuery().eq(SystemRegion::getParentId, id));
        if (!systemMenuResources.isEmpty()) {
            throw new BaseRuntimeException("存在子区划，无法删除");
        }
        return this.removeById(id);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = CacheConstant.OUTER_API_PREFIX + "base-region-tree", allEntries = true),
            @CacheEvict(value = CacheConstant.OUTER_API_PREFIX + "base-region-nodes", key = "#systemRegion.parentId", condition = "#systemRegion.parentId!=null")
    })
    public String create(String userId, SystemRegion systemRegion) {
        try {
            systemRegion.setCreator(userId);
            systemRegion.setCreateTime(LocalDateTime.now());
            this.save(systemRegion);
            return systemRegion.getId();
        } catch (Exception e) {
            if (e instanceof DuplicateKeyException) {
                throw new BaseRuntimeException("已穿在该区划代码");
            }
            throw new BaseRuntimeException("新增失败");
        }
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = CacheConstant.OUTER_API_PREFIX + "base-region-detail", key = "#systemRegion.id", condition = "#systemRegion.id!=null"),
            @CacheEvict(value = CacheConstant.OUTER_API_PREFIX + "base-region-tree", allEntries = true),
            @CacheEvict(value = CacheConstant.OUTER_API_PREFIX + "base-region-nodes", key = "#systemRegion.parentId", condition = "#systemRegion.parentId!=null")
    })
    public Boolean update(String userId, SystemRegion systemRegion) {
        systemRegion.setModifier(userId);
        systemRegion.setModifyTime(LocalDateTime.now());
        return this.updateById(systemRegion);
    }

    @Override
    public Boolean check(String region) {
        return ObjectUtil.isNotNull(this.getOne(Wrappers.<SystemRegion>lambdaQuery().eq(SystemRegion::getId, region)));
    }

}
