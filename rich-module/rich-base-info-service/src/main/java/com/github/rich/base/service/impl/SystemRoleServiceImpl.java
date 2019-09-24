package com.github.rich.base.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.rich.base.constants.CacheConstant;
import com.github.rich.base.entity.SystemRole;
import com.github.rich.base.entity.SystemRoleMenu;
import com.github.rich.base.entity.SystemUserRole;
import com.github.rich.base.mapper.SystemRoleMapper;
import com.github.rich.base.service.ISystemRoleMenuService;
import com.github.rich.base.service.ISystemRoleService;
import com.github.rich.base.service.ISystemUserRoleService;
import com.github.rich.common.core.exception.BaseRuntimeException;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

/**
 * <p>
 * 用户角色 服务实现类
 * </p>
 *
 * @author Petty
 * @since 2019-05-21
 */
@Service
public class SystemRoleServiceImpl extends ServiceImpl<SystemRoleMapper, SystemRole> implements ISystemRoleService {

    private final ISystemUserRoleService systemUserRoleService;

    private final ISystemRoleMenuService systemRoleMenuService;

    public SystemRoleServiceImpl(ISystemUserRoleService systemUserRoleService, ISystemRoleMenuService systemRoleMenuService) {
        this.systemUserRoleService = systemUserRoleService;
        this.systemRoleMenuService = systemRoleMenuService;
    }

    @Override
    public List<String> loadMenuKeysForRole(String roleId) {
        List<String> keys = new LinkedList<>();
        List<SystemRoleMenu> list = systemRoleMenuService.list(Wrappers.<SystemRoleMenu>lambdaQuery().eq(SystemRoleMenu::getRoleId, roleId));
        for (SystemRoleMenu roleMenu : list) {
            keys.add(roleMenu.getMenuId());
        }
        return keys;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = CacheConstant.SYSTEM_MENU_USER_CACHE, allEntries = true)
    public Boolean updateMenuForRole(String roleId, String[] addIds, String[] removeIds) {
        try {
            List<String> addIdList = Arrays.asList(addIds);
            List<String> removeIdList = Arrays.asList(removeIds);
            systemRoleMenuService.addBatch(roleId, addIdList);
            systemRoleMenuService.removeBatch(roleId, removeIdList);
            return true;
        } catch (Exception e) {
            throw new BaseRuntimeException("更新失败");
        }
    }

    @Override
    public IPage<SystemRole> page(SystemRole role, Page<SystemRole> page) {
        return this.page(page, Wrappers.lambdaQuery(role).orderByDesc(SystemRole::getCreateTime));
    }

    @Override
    @Cacheable(value = CacheConstant.OUTER_API_PREFIX + "base-role-detail", key = "#id", condition = "#id!=null")
    public SystemRole get(String id) {
        return this.getById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = CacheConstant.OUTER_API_PREFIX + "base-role-detail", key = "#id", condition = "#id!=null")
    public Boolean delete(String id) {
        try {
            this.removeById(id);
            systemUserRoleService.remove(Wrappers.<SystemUserRole>lambdaQuery().eq(SystemUserRole::getRoleId, id));
            systemRoleMenuService.remove(Wrappers.<SystemRoleMenu>lambdaQuery().eq(SystemRoleMenu::getRoleId, id));
            return true;
        } catch (Exception e) {
            throw new BaseRuntimeException("删除失败");
        }
    }

    @Override
    public String create(String userId, SystemRole role) {
        String roleId = IdUtil.simpleUUID();
        role.setId(roleId);
        role.setCreator(userId);
        role.setCreateTime(LocalDateTime.now());
        if (this.save(role)) {
            return roleId;
        } else {
            throw new BaseRuntimeException("新增失败");
        }
    }

    @Override
    @CacheEvict(value = CacheConstant.OUTER_API_PREFIX + "base-role-detail", key = "#role.id", condition = "#role.id!=null")
    public Boolean update(String userId, SystemRole role) {
        role.setModifier(userId);
        role.setCreateTime(LocalDateTime.now());
        return this.updateById(role);
    }

    @Override
    public List<SystemRole> findRoleByUserId(String userId) {
        List<SystemRole> systemRoles = new ArrayList<>();
        List<SystemUserRole> systemUserRoles = Optional.ofNullable(systemUserRoleService.list(Wrappers.<SystemUserRole>lambdaQuery().eq(SystemUserRole::getUserId, userId))).orElseGet(ArrayList::new);
        Set<String> roleIds = new HashSet<>();
        systemUserRoles.forEach(userRole -> roleIds.add(userRole.getRoleId()));
        if (!roleIds.isEmpty()) {
            systemRoles = Optional.ofNullable((List<SystemRole>) this.listByIds(roleIds)).orElseGet(ArrayList::new);
        }
        return systemRoles;
    }

    @Override
    public List<String> findRoleKeyByUserId(String userID) {
        List<String> keys = new ArrayList<>();
        List<SystemUserRole> systemUserRoles = systemUserRoleService.list(Wrappers.<SystemUserRole>lambdaQuery().eq(SystemUserRole::getUserId, userID));
        for (SystemUserRole systemUserRole : systemUserRoles) {
            keys.add(systemUserRole.getRoleId());
        }
        return keys;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @Caching(evict = {
            @CacheEvict(value = CacheConstant.INNER_API_PREFIX + "base-api-user", allEntries = true),
            @CacheEvict(value = CacheConstant.OUTER_API_PREFIX + "base-user-info-detail", allEntries = true),
            @CacheEvict(value = CacheConstant.SYSTEM_MENU_USER_CACHE, key = "#userId", condition = "#userId!=null")
    })
    public Boolean addUserRole(String userId, String[] roleIds) {
        try {
            systemUserRoleService.addBatch(userId, Arrays.asList(roleIds));
            return true;
        } catch (Exception e) {
            throw new BaseRuntimeException("添加失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @Caching(evict = {
            @CacheEvict(value = CacheConstant.INNER_API_PREFIX + "base-api-user", allEntries = true),
            @CacheEvict(value = CacheConstant.OUTER_API_PREFIX + "base-user-info-detail", allEntries = true),
            @CacheEvict(value = CacheConstant.SYSTEM_MENU_USER_CACHE, key = "#userId", condition = "#userId!=null")
    })
    public Boolean deleteUserRole(String userId, String[] roleIds) {
        try {
            systemUserRoleService.removeBatch(userId, Arrays.asList(roleIds));
            return true;
        } catch (Exception e) {
            throw new BaseRuntimeException("删除失败");
        }
    }

    @Override
    public Boolean check(String role) {
        return ObjectUtil.isNotNull(this.getOne(Wrappers.<SystemRole>lambdaQuery().eq(SystemRole::getRole,role)));
    }
}
