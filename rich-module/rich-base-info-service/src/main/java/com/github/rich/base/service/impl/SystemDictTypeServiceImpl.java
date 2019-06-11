package com.github.rich.base.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.rich.base.constants.CacheConstant;
import com.github.rich.base.entity.SystemDictItem;
import com.github.rich.base.entity.SystemDictType;
import com.github.rich.base.entity.SystemUser;
import com.github.rich.base.mapper.SystemDictTypeMapper;
import com.github.rich.base.service.ISystemDictItemService;
import com.github.rich.base.service.ISystemDictTypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.rich.base.vo.Dict;
import com.github.rich.common.core.exception.BaseRuntimeException;
import com.github.rich.common.core.utils.ConverterUtil;
import com.github.rich.security.utils.SecurityUtil;
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
 * 字典表 服务实现类
 * </p>
 *
 * @author Petty
 * @since 2019-05-27
 */
@Service
public class SystemDictTypeServiceImpl extends ServiceImpl<SystemDictTypeMapper, SystemDictType> implements ISystemDictTypeService {

    private final ISystemDictItemService systemDictItemService;

    public SystemDictTypeServiceImpl(ISystemDictItemService systemDictItemService) {
        this.systemDictItemService = systemDictItemService;
    }

    @Override
    @Cacheable(value = CacheConstant.DICT_ITEM_RELEVANCE_CACHE, key = "#type", condition = "#type!=null")
    public List<Dict> list(String type) {
        List<Dict> dicts = new ArrayList<>();
        Optional<SystemDictType> systemDictTypeOptional = Optional.ofNullable(this.getOne(Wrappers.<SystemDictType>lambdaQuery().eq(SystemDictType::getType, type)));
        if (systemDictTypeOptional.isPresent()) {
            List<SystemDictItem> systemDictItems = systemDictItemService.list(Wrappers.<SystemDictItem>lambdaQuery().eq(SystemDictItem::getTypeId, systemDictTypeOptional.get().getId()).orderByDesc(SystemDictItem::getSort));
            Optional<List<Dict>> optionalDictList = Optional.ofNullable(ConverterUtil.convertList(SystemDictItem.class, Dict.class, systemDictItems));
            dicts = optionalDictList.orElseGet(ArrayList::new);
        }
        return dicts;
    }

    @Override
    @Cacheable(value = CacheConstant.OUTER_API_PREFIX + "base-dict-type-page", key = "T(String).valueOf(#page.current).concat('-').concat(T(String).valueOf(#page.size)).concat('-').concat(#dictType.toString())")
    public IPage<SystemDictType> page(SystemDictType dictType, Page<SystemDictType> page) {
        return super.page(page,Wrappers.lambdaQuery(dictType).orderByDesc(SystemDictType::getCreateTime));
    }

    @Override
    @Cacheable(value = CacheConstant.OUTER_API_PREFIX + "base-dict-type-detail", key = "#id", condition = "#id!=null")
    public SystemDictType get(String id) {
        return this.getById(id);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = CacheConstant.OUTER_API_PREFIX + "base-dict-type-page", allEntries = true),
            @CacheEvict(value = CacheConstant.OUTER_API_PREFIX + "base-dict-type-detail", key = "#id", condition = "#id!=null"),
            @CacheEvict(value = CacheConstant.DICT_ITEM_RELEVANCE_CACHE, allEntries = true)
    })
    public Integer delete(String id) {
        List<SystemDictItem> dictItems = systemDictItemService.list(Wrappers.<SystemDictItem>lambdaQuery().eq(SystemDictItem::getTypeId, id));
        if (!dictItems.isEmpty()) {
            return 2;
        }
        return this.removeById(id) ? 1 : 0;
    }

    @Override
    @CacheEvict(value = CacheConstant.OUTER_API_PREFIX + "base-dict-type-page", allEntries = true)
    public String create(SystemDictType dictType) {
        String dictTypeId = IdUtil.simpleUUID();
        dictType.setId(dictTypeId);
        dictType.setCreator(Objects.requireNonNull(SecurityUtil.getUser()).getUserId());
        dictType.setCreateTime(LocalDateTime.now());
        if(this.save(dictType)){
            return dictTypeId;
        }else {
            throw new BaseRuntimeException("新增失败");
        }
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = CacheConstant.OUTER_API_PREFIX + "base-dict-type-page", allEntries = true),
            @CacheEvict(value = CacheConstant.OUTER_API_PREFIX + "base-dict-type-detail", key = "#dictType.id", condition = "#dictType.id!=null"),
            @CacheEvict(value = CacheConstant.DICT_ITEM_RELEVANCE_CACHE, key = "#dictType.type", condition = "#dictType.type!=null")
    })
    public Boolean update(SystemDictType dictType) {
        dictType.setModifier(Objects.requireNonNull(SecurityUtil.getUser()).getUserId());
        dictType.setModifierTime(LocalDateTime.now());
        return this.updateById(dictType);
    }
}
