package com.github.rich.base.service;

import com.github.rich.base.entity.SystemRoleMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 角色菜单关联 服务类
 * </p>
 *
 * @author Petty
 * @since 2019-05-21
 */
public interface ISystemRoleMenuService extends IService<SystemRoleMenu> {

    /**
     * 批量增加
     * @param roleId 角色ID
     * @param addIdList Menu ID集合
     */
    void addBatch(String roleId, List<String> addIdList);

    /**
     * 批量删除
     * @param roleId 角色ID
     * @param removeIdList Menu ID集合
     */
    void removeBatch(String roleId, List<String> removeIdList);
}
