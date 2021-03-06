package com.github.rich.base.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.rich.base.constants.CacheConstant;
import com.github.rich.base.entity.SystemDictItem;
import com.github.rich.base.mapper.SystemDictItemMapper;
import com.github.rich.base.service.ISystemDictItemService;
import com.github.rich.common.core.exception.BaseRuntimeException;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Petty
 * @since 2019-05-27
 */
@Service
public class SystemDictItemServiceImpl extends ServiceImpl<SystemDictItemMapper, SystemDictItem> implements ISystemDictItemService {

    @Override
    @Cacheable(value = CacheConstant.OUTER_API_PREFIX + "base-dict-item-page", key = "T(String).valueOf(#page.current).concat('-').concat(T(String).valueOf(#page.size)).concat('-').concat(#typeId)")
    public IPage<SystemDictItem> page(String typeId, Page<SystemDictItem> page) {
        return super.page(page, Wrappers.<SystemDictItem>lambdaQuery().eq(SystemDictItem::getTypeId, typeId).orderByDesc(SystemDictItem::getCreateTime));
    }

    @Override
    @Cacheable(value = CacheConstant.OUTER_API_PREFIX + "base-dict-item-detail", key = "#id", condition = "#id!=null")
    public SystemDictItem get(String id) {
        return this.getById(id);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = CacheConstant.OUTER_API_PREFIX + "base-dict-item-page", allEntries = true),
            @CacheEvict(value = CacheConstant.DICT_ITEM_RELEVANCE_CACHE, allEntries = true),
            @CacheEvict(value = CacheConstant.OUTER_API_PREFIX + "base-dict-item-detail", key = "#id", condition = "#id!=null")
    })
    public Boolean delete(String id) {
        return this.removeById(id);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = CacheConstant.OUTER_API_PREFIX + "base-dict-item-page", allEntries = true),
            @CacheEvict(value = CacheConstant.DICT_ITEM_RELEVANCE_CACHE, allEntries = true),
            @CacheEvict(value = CacheConstant.OUTER_API_PREFIX + "base-dict-item-detail", allEntries = true)
    })
    public Boolean deleteByCodes(List<String> ids) {
        return this.removeByIds(ids);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = CacheConstant.OUTER_API_PREFIX + "base-dict-item-page", allEntries = true),
            @CacheEvict(value = CacheConstant.DICT_ITEM_RELEVANCE_CACHE, allEntries = true)
    })
    public String create(String userId, SystemDictItem dictItem) {
        dictItem.setCreator(userId);
        dictItem.setCreateTime(LocalDateTime.now());
        if (this.save(dictItem)) {
            return dictItem.getId();
        } else {
            throw new BaseRuntimeException("新增失败");
        }
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = CacheConstant.OUTER_API_PREFIX + "base-dict-item-page", allEntries = true),
            @CacheEvict(value = CacheConstant.DICT_ITEM_RELEVANCE_CACHE, allEntries = true),
            @CacheEvict(value = CacheConstant.OUTER_API_PREFIX + "base-dict-item-detail", key = "#dictItem.id", condition = "#dictItem.id!=null")
    })
    public Boolean update(String userId, SystemDictItem dictItem) {
        dictItem.setModifier(userId);
        dictItem.setModifyTime(LocalDateTime.now());
        return this.updateById(dictItem);
    }
}
