package com.github.rich.base.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.rich.base.entity.SystemDictItem;
import com.github.rich.base.mapper.SystemDictItemMapper;
import com.github.rich.base.service.ISystemDictItemService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.rich.security.utils.SecurityUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

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
    public IPage<SystemDictItem> page(String typeCode, Page<SystemDictItem> page) {
        return super.page(page, Wrappers.<SystemDictItem>lambdaQuery().eq(SystemDictItem::getTypeCode, typeCode));
    }

    @Override
    public SystemDictItem get(String code) {
        return this.getById(code);
    }

    @Override
    public Boolean delete(String code) {
        return this.removeById(code);
    }

    @Override
    public String create(SystemDictItem dictItem) {
        String dictItemCode = IdUtil.simpleUUID();
        dictItem.setId(IdUtil.simpleUUID());
        dictItem.setCode(dictItemCode);
        dictItem.setCreator(Objects.requireNonNull(SecurityUtil.getUser()).getUserCode());
        dictItem.setCreateTime(LocalDateTime.now());
        return this.save(dictItem) ? dictItemCode : null;
    }

    @Override
    public Boolean update(SystemDictItem dictItem) {
        dictItem.setModifier(Objects.requireNonNull(SecurityUtil.getUser()).getUserCode());
        dictItem.setModifierTime(LocalDateTime.now());
        return this.updateById(dictItem);
    }
}
