package com.github.rich.base.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.rich.base.entity.SystemDictItem;
import com.github.rich.base.entity.SystemDictType;
import com.github.rich.base.mapper.SystemDictTypeMapper;
import com.github.rich.base.service.ISystemDictItemService;
import com.github.rich.base.service.ISystemDictTypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.rich.security.utils.SecurityUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

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

    private final int DELETE_ERR_CHILDREN = 2;
    private final int DELETE_SUCCESS = 1;
    private final int DELETE_FAIL = 0;

    private final ISystemDictItemService systemDictItemService;

    public SystemDictTypeServiceImpl(ISystemDictItemService systemDictItemService) {
        this.systemDictItemService = systemDictItemService;
    }

    @Override
    public IPage<SystemDictType> page(SystemDictType dictType, Page<SystemDictType> page) {
        return super.page(page);
    }

    @Override
    public SystemDictType get(String code) {
        return this.getById(code);
    }

    @Override
    public Integer delete(String code) {
        List<SystemDictItem> dictItems = systemDictItemService.list(Wrappers.<SystemDictItem>lambdaQuery().eq(SystemDictItem::getTypeCode, code));
        if (!dictItems.isEmpty()) {
            return DELETE_ERR_CHILDREN;
        }
        return this.removeById(code) ? DELETE_SUCCESS : DELETE_FAIL;
    }

    @Override
    public String create(SystemDictType dictType) {
        String dictTypeCode = IdUtil.simpleUUID();
        dictType.setId(IdUtil.simpleUUID());
        dictType.setCode(dictTypeCode);
        dictType.setCreator(Objects.requireNonNull(SecurityUtil.getUser()).getUserCode());
        dictType.setCreateTime(LocalDateTime.now());
        return this.save(dictType) ? dictTypeCode : null;
    }

    @Override
    public Boolean update(SystemDictType dictType) {
        dictType.setModifier(Objects.requireNonNull(SecurityUtil.getUser()).getUserCode());
        dictType.setModifierTime(LocalDateTime.now());
        return this.updateById(dictType);
    }
}
