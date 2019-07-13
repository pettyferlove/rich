package com.github.rich.base.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.rich.base.constants.CacheConstant;
import com.github.rich.base.entity.SystemMenuResource;
import com.github.rich.base.entity.SystemRoleMenu;
import com.github.rich.base.entity.SystemUserRole;
import com.github.rich.base.mapper.SystemMenuResourceMapper;
import com.github.rich.base.service.ISystemMenuResourceService;
import com.github.rich.base.service.ISystemRoleMenuService;
import com.github.rich.base.service.ISystemUserRoleService;
import com.github.rich.base.utils.TreeUtils;
import com.github.rich.base.vo.MenuNode;
import com.github.rich.common.core.exception.BaseRuntimeException;
import com.github.rich.common.core.utils.ConverterUtil;
import com.github.rich.security.config.SystemSecurityProperties;
import com.github.rich.security.service.impl.UserDetailsImpl;
import com.github.rich.security.utils.SecurityUtil;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

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

    private final SystemSecurityProperties systemSecurityProperties;

    private final ISystemUserRoleService systemUserRoleService;

    private final ISystemRoleMenuService systemRoleMenuService;

    public SystemMenuResourceServiceImpl(SystemSecurityProperties systemSecurityProperties, ISystemUserRoleService systemUserRoleService, ISystemRoleMenuService systemRoleMenuService) {
        this.systemSecurityProperties = systemSecurityProperties;
        this.systemUserRoleService = systemUserRoleService;
        this.systemRoleMenuService = systemRoleMenuService;
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = CacheConstant.OUTER_API_PREFIX + "base-menu-tree", allEntries = true),
            @CacheEvict(value = CacheConstant.SYSTEM_MENU_USER_CACHE, allEntries = true),
            @CacheEvict(value = CacheConstant.OUTER_API_PREFIX + "base-menu-children-tree", key = "#menu.parentId", condition = "#menu.parentId!=null")
    })
    public String createNode(SystemMenuResource menu) {
        String menuId = IdUtil.simpleUUID();
        menu.setId(menuId);
        menu.setCreator(Objects.requireNonNull(SecurityUtil.getUser()).getUserId());
        menu.setCreateTime(LocalDateTime.now());
        if (this.save(menu)) {
            return menuId;
        } else {
            throw new BaseRuntimeException("新增失败");
        }
    }

    @Override
    @Cacheable(value = CacheConstant.OUTER_API_PREFIX + "base-menu-tree")
    public List<MenuNode> loadTree() {
        List<SystemMenuResource> systemMenuResources = this.list(Wrappers.<SystemMenuResource>lambdaQuery().orderByDesc(SystemMenuResource::getSort));
        return TreeUtils.buildTree(Optional.ofNullable(ConverterUtil.convertList(SystemMenuResource.class, MenuNode.class, systemMenuResources)).orElseGet(ArrayList::new), "0");
    }

    @Override
    @Cacheable(value = CacheConstant.SYSTEM_MENU_USER_CACHE, key = "#userDetails.userId", condition = "#userDetails.userId!=null")
    public List<MenuNode> loadMenu(UserDetailsImpl userDetails) {
        if (StrUtil.isNotEmpty(systemSecurityProperties.getAdminName()) && StrUtil.isNotEmpty(systemSecurityProperties.getAdminPassword())) {
            assert userDetails != null;
            if (systemSecurityProperties.getAdminName().equals(userDetails.getUsername())) {
                List<SystemMenuResource> systemMenuResources = this.list(Wrappers.<SystemMenuResource>lambdaQuery().eq(SystemMenuResource::getPermissionType, 0).orderByDesc(SystemMenuResource::getSort));
                Set<SystemMenuResource> systemMenuResourceSet = new LinkedHashSet<>(systemMenuResources);
                return TreeUtils.buildTree(Optional.ofNullable(ConverterUtil.convertList(SystemMenuResource.class, MenuNode.class, new ArrayList<>(systemMenuResourceSet))).orElseGet(ArrayList::new), "0");
            }
        }
        assert userDetails != null;
        String userId = userDetails.getUserId();
        List<SystemUserRole> systemUserRoles = systemUserRoleService.list(Wrappers.<SystemUserRole>lambdaQuery().eq(SystemUserRole::getUserId, userId));
        List<String> roleIds = new ArrayList<>();
        for (SystemUserRole systemUserRole : systemUserRoles) {
            roleIds.add(systemUserRole.getRoleId());
        }
        if (roleIds.size() > 0) {
            List<String> menuIds = new ArrayList<>();
            List<SystemRoleMenu> systemRoleMenus = systemRoleMenuService.list(Wrappers.<SystemRoleMenu>lambdaQuery().in(SystemRoleMenu::getRoleId, roleIds));
            for (SystemRoleMenu systemRoleMenu : systemRoleMenus) {
                menuIds.add(systemRoleMenu.getMenuId());
            }
            if (menuIds.size() > 0) {
                List<SystemMenuResource> systemMenuResources = this.list(Wrappers.<SystemMenuResource>lambdaQuery().in(SystemMenuResource::getId, menuIds).eq(SystemMenuResource::getPermissionType, 0).orderByDesc(SystemMenuResource::getSort));
                Set<SystemMenuResource> systemMenuResourceSet = new LinkedHashSet<>(systemMenuResources);
                return TreeUtils.buildTree(Optional.ofNullable(ConverterUtil.convertList(SystemMenuResource.class, MenuNode.class, new ArrayList<>(systemMenuResourceSet))).orElseGet(ArrayList::new), "0");
            }
        }
        return new ArrayList<>();
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
            @CacheEvict(value = CacheConstant.SYSTEM_MENU_USER_CACHE, allEntries = true),
            @CacheEvict(value = CacheConstant.OUTER_API_PREFIX + "base-menu-children-tree", allEntries = true)
    })
    @Transactional(rollbackFor = Throwable.class)
    public Boolean deleteNode(String id) {
        List<SystemMenuResource> systemMenuResources = this.list(Wrappers.<SystemMenuResource>lambdaQuery().eq(SystemMenuResource::getParentId, id));
        systemRoleMenuService.remove(Wrappers.<SystemRoleMenu>lambdaQuery().eq(SystemRoleMenu::getMenuId,id));
        if (!systemMenuResources.isEmpty()) {
            throw new BaseRuntimeException("存在子节点，无法删除");
        }
        return this.removeById(id);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = CacheConstant.OUTER_API_PREFIX + "base-menu-node-detail", key = "#menu.id", condition = "#menu.id!=null"),
            @CacheEvict(value = CacheConstant.OUTER_API_PREFIX + "base-menu-tree", allEntries = true),
            @CacheEvict(value = CacheConstant.SYSTEM_MENU_USER_CACHE, allEntries = true),
            @CacheEvict(value = CacheConstant.OUTER_API_PREFIX + "base-menu-children-tree", key = "#menu.parentId", condition = "#menu.parentId!=null")
    })
    public Boolean updateNode(SystemMenuResource menu) {
        menu.setModifier(Objects.requireNonNull(SecurityUtil.getUser()).getUserId());
        menu.setModifierTime(LocalDateTime.now());
        return this.updateById(menu);
    }

    @Override
    @Cacheable(value = CacheConstant.OUTER_API_PREFIX + "base-menu-children-tree", key = "#parentId", condition = "#parentId!=null")
    public List<MenuNode> loadChildrenNodes(String parentId) {
        List<SystemMenuResource> systemMenuResources = this.list(Wrappers.<SystemMenuResource>lambdaQuery().orderByDesc(SystemMenuResource::getSort));
        return TreeUtils.buildTree(Optional.ofNullable(ConverterUtil.convertList(SystemMenuResource.class, MenuNode.class, systemMenuResources)).orElseGet(ArrayList::new), parentId);
    }

    @Override
    public List<SystemMenuResource> loadPermissionsByUserId(String userId) {
        List<SystemUserRole> systemUserRoles = systemUserRoleService.list(Wrappers.<SystemUserRole>lambdaQuery().eq(SystemUserRole::getUserId, userId));
        List<String> roleIds = new ArrayList<>();
        for (SystemUserRole systemUserRole : systemUserRoles) {
            roleIds.add(systemUserRole.getRoleId());
        }
        if (roleIds.size() > 0) {
            List<String> menuIds = new ArrayList<>();
            List<SystemRoleMenu> systemRoleMenus = systemRoleMenuService.list(Wrappers.<SystemRoleMenu>lambdaQuery().in(SystemRoleMenu::getRoleId, roleIds));
            for (SystemRoleMenu systemRoleMenu : systemRoleMenus) {
                menuIds.add(systemRoleMenu.getMenuId());
            }
            if (menuIds.size() > 0) {
                return this.list(Wrappers.<SystemMenuResource>lambdaQuery().in(SystemMenuResource::getId, menuIds).eq(SystemMenuResource::getPermissionType, 1));
            }
        }
        return new ArrayList<>();
    }
}
