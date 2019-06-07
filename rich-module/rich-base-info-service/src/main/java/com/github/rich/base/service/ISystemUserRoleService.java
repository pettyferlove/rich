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
     * 批量增加
     * @param userId 用户ID
     * @param addIdList Role ID集合
     */
    void addBatch(String userId, List<String> addIdList);

    /**
     * 批量删除
     * @param userId 用户ID
     * @param removeIdList Role ID集合
     */
    void removeBatch(String userId, List<String> removeIdList);

}
