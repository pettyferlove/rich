package com.github.rich.base.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.rich.base.entity.SystemRole;

import java.util.List;

/**
 * <p>
 * 用户角色 服务类
 * </p>
 *
 * @author Petty
 * @since 2019-05-21
 */
public interface ISystemRoleService extends IService<SystemRole> {

    /**
     * 加载当前roleId已分配菜单的Keys
     *
     * @param roleId roleId
     * @return 集合
     */
    List<String> loadMenuKeysForRole(String roleId);

    /**
     * 更绝角色菜单关联信息
     *
     * @param roleId    角色ID
     * @param addIds    需要添加的ID集合
     * @param removeIds 需要删除的ID集合
     * @return Boolean
     */
    Boolean updateMenuForRole(String roleId, String[] addIds, String[] removeIds);

    /**
     * List查找
     *
     * @param role 查询参数对象
     * @param page Page分页对象
     * @return IPage 返回结果
     */
    IPage<SystemRole> page(SystemRole role, Page<SystemRole> page);

    /**
     * 通过Id查询Role信息
     *
     * @param id 业务主键
     * @return 对象
     */
    SystemRole get(String id);

    /**
     * 通过Id删除信息
     *
     * @param id 业务主键
     * @return Boolean
     */
    Boolean delete(String id);

    /**
     * 创建数据
     *
     * @param userId userId
     * @param role   要创建的对象
     * @return String
     */
    String create(String userId, SystemRole role);

    /**
     * 更新数据（必须带Id）
     *
     * @param userId userId
     * @param role   对象
     * @return Boolean
     */
    Boolean update(String userId, SystemRole role);

    /**
     * 通过用户名查询角色集合
     *
     * @param userId 用户ID
     * @return 集合
     */
    List<SystemRole> findRoleByUserId(String userId);

    /**
     * 通过用户名查询角色ID集合
     *
     * @param userID 用户ID
     * @return 集合
     */
    List<String> findRoleKeyByUserId(String userID);

    /**
     * 为用户添加角色
     *
     * @param userId  用户ID
     * @param roleIds 需要添加得Role ID集合
     * @return Boolean
     */
    Boolean addUserRole(String userId, String[] roleIds);

    /**
     * 删除用户角色
     *
     * @param userId  用户ID
     * @param roleIds 需要删除得Role ID集合
     * @return Boolean
     */
    Boolean deleteUserRole(String userId, String[] roleIds);

    /**
     * 检查角色是否存在
     *
     * @param role 角色名（CODE）
     * @return 存在True 不存在False
     */
    Boolean check(String role);
}
