package com.github.rich.base.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.rich.base.entity.SystemRole;
import com.baomidou.mybatisplus.extension.service.IService;

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
     * @param roleId roleId
     * @return 集合
     */
    List<String> loadMenuKeysForRole(String roleId);

    /**
     * List查找
     *
     * @param role 查询参数对象
     * @param page     Page分页对象
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
     * @param role 要创建的对象
     * @return Boolean
     */
    String create(SystemRole role);

    /**
     * 更新数据（必须带Code）
     *
     * @param role 对象
     * @return Boolean
     */
    Boolean update(SystemRole role);

    /**
     * 通过用户名查询角色集合
     * @param userId 用户ID
     * @return 集合
     */
    List<SystemRole> findRoleByUserId(String userId);
}
