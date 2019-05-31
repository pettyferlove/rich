package com.github.rich.base.service;

import com.github.rich.base.entity.SystemRole;
import com.github.rich.base.entity.SystemUserRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 用户角色关联信息 服务类
 * </p>
 *
 * @author Petty
 * @since 2019-04-23
 */
public interface ISystemUserRoleService extends IService<SystemUserRole> {

    /**
     * 通过用户名查询角色集合
     * @param userId 用户ID
     * @return 集合
     */
    List<SystemRole> findRoleByUserId(String userId);
}
