package com.github.rich.base.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.rich.base.constant.CacheConstant;
import com.github.rich.base.entity.SystemRole;
import com.github.rich.base.entity.SystemUser;
import com.github.rich.base.entity.SystemUserRole;
import com.github.rich.base.mapper.SystemUserRoleMapper;
import com.github.rich.base.service.ISystemRoleService;
import com.github.rich.base.service.ISystemUserRoleService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 * 用户角色关联信息 服务实现类
 * </p>
 *
 * @author Petty
 * @since 2019-04-23
 */
@Service
public class SystemUserRoleServiceImpl extends ServiceImpl<SystemUserRoleMapper, SystemUserRole> implements ISystemUserRoleService {

    private final ISystemRoleService systemRoleService;

    public SystemUserRoleServiceImpl(ISystemRoleService systemRoleService) {
        this.systemRoleService = systemRoleService;
    }

    @Override
    @Cacheable(value = CacheConstant.API_PREFIX + "base-api-role", key = "#userCode", condition = "#userCode!=null")
    public List<SystemRole> findRoleByUserCode(String userCode) {
        List<SystemRole> systemRoles = new ArrayList<>();
        List<SystemUserRole> systemUserRoles = Optional.ofNullable(this.list(Wrappers.<SystemUserRole>lambdaQuery().eq(SystemUserRole::getUserCode, userCode))).orElseGet(ArrayList::new);
        Set<String> roleIds = new HashSet<>();
        systemUserRoles.forEach(userRole -> roleIds.add(userRole.getRoleCode()));
        if (!roleIds.isEmpty()) {
            systemRoles = Optional.ofNullable((List<SystemRole>) systemRoleService.listByIds(roleIds)).orElseGet(ArrayList::new);
        }
        return systemRoles;
    }
}
