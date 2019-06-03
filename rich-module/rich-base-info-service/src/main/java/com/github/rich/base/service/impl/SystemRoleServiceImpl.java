package com.github.rich.base.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.rich.base.entity.SystemRole;
import com.github.rich.base.entity.SystemRoleMenu;
import com.github.rich.base.entity.SystemUserRole;
import com.github.rich.base.mapper.SystemRoleMapper;
import com.github.rich.base.service.ISystemRoleMenuService;
import com.github.rich.base.service.ISystemRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.rich.base.service.ISystemUserRoleService;
import com.github.rich.common.core.exception.BaseRuntimeException;
import com.github.rich.security.utils.SecurityUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;

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
    public IPage<SystemRole> page(SystemRole role, Page<SystemRole> page) {
        return this.page(page, Wrappers.lambdaQuery(role));
    }

    @Override
    public SystemRole get(String id) {
        return this.getById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean delete(String id) {
        try {
            this.removeById(id);
            systemUserRoleService.remove(Wrappers.<SystemUserRole>lambdaQuery().eq(SystemUserRole::getRoleId,id));
            systemRoleMenuService.remove(Wrappers.<SystemRoleMenu>lambdaQuery().eq(SystemRoleMenu::getRoleId,id));
            return true;
        }catch (Exception e){
            throw new BaseRuntimeException("删除失败");
        }
    }

    @Override
    public String create(SystemRole role) {
        String roleId = IdUtil.simpleUUID();
        role.setId(roleId);
        role.setCreator(Objects.requireNonNull(SecurityUtil.getUser()).getUserId());
        role.setCreateTime(LocalDateTime.now());
        if(this.save(role)){
            return roleId;
        }else {
            throw new BaseRuntimeException("save error");
        }
    }

    @Override
    public Boolean update(SystemRole role) {
        role.setModifier(Objects.requireNonNull(SecurityUtil.getUser()).getUserId());
        role.setModifierTime(LocalDateTime.now());
        return this.updateById(role);
    }
}
