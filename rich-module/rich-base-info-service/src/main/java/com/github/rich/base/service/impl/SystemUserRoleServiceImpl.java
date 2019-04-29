package com.github.rich.base.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.rich.base.entity.SystemRole;
import com.github.rich.base.entity.SystemUserRole;
import com.github.rich.base.mapper.SystemUserRoleMapper;
import com.github.rich.base.service.ISystemRoleService;
import com.github.rich.base.service.ISystemUserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    public List<SystemRole> findRoleByUserCode(String userCode) {
        List<SystemRole> systemRoles = new ArrayList<>();
        List<SystemUserRole> systemUserRoles = this.list(new QueryWrapper<SystemUserRole>().eq("user_code", userCode));
        Set<String> roleIds = new HashSet<>();
        systemUserRoles.forEach(userRole-> roleIds.add(userRole.getRoleCode()));
        if(!roleIds.isEmpty()){
            systemRoles = (List<SystemRole>) systemRoleService.listByIds(roleIds);
        }
        return systemRoles;
    }
}
