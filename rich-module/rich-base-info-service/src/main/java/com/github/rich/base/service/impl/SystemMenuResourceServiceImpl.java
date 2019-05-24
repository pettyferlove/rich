package com.github.rich.base.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.rich.base.entity.SystemMenuResource;
import com.github.rich.base.mapper.SystemMenuResourceMapper;
import com.github.rich.base.service.ISystemMenuResourceService;
import com.github.rich.base.utils.TreeUtils;
import com.github.rich.base.vo.MenuNode;
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

    @Override
    public MenuNode getNode(String code) {
        return Optional.ofNullable(ConverterUtil.convert(this.getById(code), new MenuNode())).orElseGet(MenuNode::new);
    }

    @Override
    public Boolean deleteNode(String code) throws Exception {
        List<SystemMenuResource> systemMenuResources = this.list(Wrappers.<SystemMenuResource>lambdaQuery().eq(SystemMenuResource::getParentCode,code));
        if(!systemMenuResources.isEmpty()){
            throw new BaseRuntimeException("存在子节点，无法删除");
        }
        return this.removeById(code);
    }

    @Override
    public Boolean updateNode(SystemMenuResource menu) {
        menu.setModifier(Objects.requireNonNull(SecurityUtil.getUser()).getUserCode());
        menu.setModifierTime(LocalDateTime.now());
        return this.updateById(menu);
    }

    @Override
    public List<MenuNode> loadChildrenNodes(String parentCode) {
        List<SystemMenuResource> systemMenuResources = this.list(Wrappers.emptyWrapper());
        return TreeUtils.buildTree(Optional.ofNullable(ConverterUtil.convertList(SystemMenuResource.class, MenuNode.class, systemMenuResources)).orElseGet(ArrayList::new), parentCode);
    }
}
