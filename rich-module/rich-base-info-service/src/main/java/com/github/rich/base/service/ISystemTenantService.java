package com.github.rich.base.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.rich.base.entity.SystemTenant;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.rich.base.vo.TenantVO;

import java.util.List;

/**
 * <p>
 * 租户信息 服务类
 * </p>
 *
 * @author Petty
 * @since 2019-07-13
 */
public interface ISystemTenantService extends IService<SystemTenant> {

    /**
     * List查找
     *
     * @param systemTenant 查询参数对象
     * @param page     Page分页对象
     * @return IPage 返回结果
     */
    IPage<SystemTenant> page(SystemTenant systemTenant, Page<SystemTenant> page);


    /**
     * 获取全部租户
     * @return TenantVO VO类
     */
    List<TenantVO> all();

    /**
     * 通过Id查询SystemTenant信息
     *
     * @param id 业务主键
     * @return 对象
     */
    SystemTenant get(String id);

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
      * @param systemTenant 要创建的对象
      * @return Boolean
      */
     String create(SystemTenant systemTenant);

     /**
      * 更新数据（必须带Id）
      *
      * @param systemTenant 对象
      * @return Boolean
      */
     Boolean update(SystemTenant systemTenant);

    /**
     * 修改状态
     * @param tenant SystemTenant
     * @return Boolean
     */
    Boolean changeStatus(SystemTenant tenant);

    /**
     * 检查租户是否存在
     * @param tenantId 租户ID
     * @return 存在True 不存在False
     */
    Boolean check(String tenantId);

    /**
     * 检查租户当前是否有效
     * @param tenantId tenantId
     * @return True 有效 False 无效
     */
    Boolean checkTenantStatus(String tenantId);

}
