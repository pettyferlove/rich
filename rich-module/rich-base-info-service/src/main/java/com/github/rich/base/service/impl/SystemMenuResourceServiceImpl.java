package com.github.rich.base.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.rich.base.entity.SystemMenuResource;
import com.github.rich.base.mapper.SystemMenuResourceMapper;
import com.github.rich.base.service.ISystemMenuResourceService;
import com.github.rich.base.utils.TreeUtils;
import com.github.rich.base.vo.MenuNode;
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

    @Override
    public List<MenuNode> loadTree() {
        List<SystemMenuResource> systemMenuResources = this.list(Wrappers.emptyWrapper());
        return TreeUtils.buildTree(Optional.ofNullable(ConverterUtil.convertList(SystemMenuResource.class, MenuNode.class, systemMenuResources)).orElseGet(ArrayList::new), "0");
    }
}
