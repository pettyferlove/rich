package com.github.rich.base.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.rich.base.entity.SystemUserRole;
import com.github.rich.base.mapper.SystemUserRoleMapper;
import com.github.rich.base.service.ISystemUserRoleService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public void addBatch(String userId, List<String> addIdList) {
        List<SystemUserRole> systemUserRoles = new ArrayList<>();
        for (String roleId : addIdList) {
            SystemUserRole systemUserRole = new SystemUserRole();
            systemUserRole.setRoleId(roleId);
            systemUserRole.setUserId(userId);
            systemUserRoles.add(systemUserRole);
        }
        if (systemUserRoles.size() != 0) {
            this.saveBatch(systemUserRoles);
        }
    }

    @Override
    public void removeBatch(String userId, List<String> removeIdList) {
        if (removeIdList.size() > 0) {
            List<SystemUserRole> systemUserRoles = this.list(Wrappers.<SystemUserRole>lambdaQuery().eq(SystemUserRole::getUserId, userId).in(SystemUserRole::getRoleId, removeIdList));
            List<String> ids = new ArrayList<>();
            for (SystemUserRole systemUserRole : systemUserRoles) {
                ids.add(systemUserRole.getId());
            }
            if (ids.size() > 0) {
                this.removeByIds(ids);
            }
        }
    }
}
