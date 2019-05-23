package com.github.rich.base.service.impl;

import cn.hutool.core.util.IdUtil;
import com.github.rich.base.entity.SystemMenuResource;
import com.github.rich.base.mapper.SystemMenuResourceMapper;
import com.github.rich.base.service.ISystemMenuResourceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.rich.security.utils.SecurityUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Petty
 * @since 2019-05-21
 */
@Service
public class SystemMenuResourceServiceImpl extends ServiceImpl<SystemMenuResourceMapper, SystemMenuResource> implements ISystemMenuResourceService {

    @Override
    public String createNode(SystemMenuResource menu) {
        String menuCode = IdUtil.simpleUUID();
        menu.setId(IdUtil.simpleUUID());
        menu.setCode(menuCode);
        menu.setCreator(Objects.requireNonNull(SecurityUtil.getUser()).getUserCode());
        menu.setCreateTime(LocalDateTime.now());
        return this.save(menu)?menuCode:null;
    }
}
