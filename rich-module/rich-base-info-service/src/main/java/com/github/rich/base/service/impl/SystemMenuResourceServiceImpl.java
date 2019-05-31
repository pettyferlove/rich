package com.github.rich.base.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.rich.base.constant.CacheConstant;
import com.github.rich.base.entity.SystemMenuResource;
import com.github.rich.base.mapper.SystemMenuResourceMapper;
import com.github.rich.base.service.ISystemMenuResourceService;
import com.github.rich.base.utils.TreeUtils;
import com.github.rich.base.vo.MenuNode;
import com.github.rich.common.core.exception.BaseRuntimeException;
import com.github.rich.common.core.utils.ConverterUtil;
import com.github.rich.security.utils.SecurityUtil;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Petty
 * @since 2019-05-21
 */
@Service
public class SystemMenuResourceServiceImpl extends ServiceImpl<SystemMenuResourceMapper, SystemMenuResource> implements ISystemMenuResourceService {

    @Override
    @Caching(evict = {
            @CacheEvict(value = CacheConstant.OUTER_API_PREFIX + "base-menu-tree", allEntries = true),
            @CacheEvict(value = CacheConstant.OUTER_API_PREFIX + "base-menu-children-tree",key = "#menu.parentId",condition = "#menu.parentId!=null")
    })
    public String createNode(SystemMenuResource menu) {
        String menuCode = IdUtil.simpleUUID();
        menu.setId(menuCode);
        menu.setCreator(Objects.requireNonNull(SecurityUtil.getUser()).getUserId());
        menu.setCreateTime(LocalDateTime.now());
        return this.save(menu) ? menuCode : null;
    }

    @Override
    @Cacheable(value = CacheConstant.OUTER_API_PREFIX + "base-menu-tree")
    public List<MenuNode> loadTree() {
        List<SystemMenuResource> systemMenuResources = this.list(Wrappers.<SystemMenuResource>lambdaQuery().orderByAsc(SystemMenuResource::getSort));
        return TreeUtils.buildTree(Optional.ofNullable(ConverterUtil.convertList(SystemMenuResource.class, MenuNode.class, systemMenuResources)).orElseGet(ArrayList::new), "0");
    }

    @Override
    @Cacheable(value = CacheConstant.OUTER_API_PREFIX + "base-menu-node-detail", key = "#id", condition = "#id!=null")
    public MenuNode getNode(String id) {
        return Optional.ofNullable(ConverterUtil.convert(this.getById(id), new MenuNode())).orElseGet(MenuNode::new);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = CacheConstant.OUTER_API_PREFIX + "base-menu-node-detail", key = "#id"),
            @CacheEvict(value = CacheConstant.OUTER_API_PREFIX + "base-menu-tree", allEntries = true),
            @CacheEvict(value = CacheConstant.OUTER_API_PREFIX + "base-menu-children-tree", allEntries = true)
    })
    public Boolean deleteNode(String id) throws Exception {
        List<SystemMenuResource> systemMenuResources = this.list(Wrappers.<SystemMenuResource>lambdaQuery().eq(SystemMenuResource::getParentId, id));
        if (!systemMenuResources.isEmpty()) {
            throw new BaseRuntimeException("存在子节点，无法删除");
        }
        return this.removeById(id);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = CacheConstant.OUTER_API_PREFIX + "base-menu-node-detail", key = "#menu.id",condition = "#menu.id!=null"),
            @CacheEvict(value = CacheConstant.OUTER_API_PREFIX + "base-menu-tree", allEntries = true),
            @CacheEvict(value = CacheConstant.OUTER_API_PREFIX + "base-menu-children-tree",key = "#menu.parentId",condition = "#menu.parentId!=null")
    })
    public Boolean updateNode(SystemMenuResource menu) {
        menu.setModifier(Objects.requireNonNull(SecurityUtil.getUser()).getUserId());
        menu.setModifierTime(LocalDateTime.now());
        return this.updateById(menu);
    }

    @Override
    @Cacheable(value = CacheConstant.OUTER_API_PREFIX + "base-menu-children-tree",key = "#parentId",condition = "#parentId!=null")
    public List<MenuNode> loadChildrenNodes(String parentId) {
        List<SystemMenuResource> systemMenuResources = this.list(Wrappers.<SystemMenuResource>lambdaQuery().orderByAsc(SystemMenuResource::getSort));
        return TreeUtils.buildTree(Optional.ofNullable(ConverterUtil.convertList(SystemMenuResource.class, MenuNode.class, systemMenuResources)).orElseGet(ArrayList::new), parentId);
    }
}
