package com.github.rich.base.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.rich.base.dto.Route;
import com.github.rich.base.entity.SystemGatewayRoute;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Petty
 * @since 2019-05-14
 */
public interface ISystemGatewayRouteService extends IService<SystemGatewayRoute> {

    /**
     * 加载全部Route
     * @return 集合
     */
    List<Route> loadAll();

    /**
     * 查询Route列表
     * @param route SystemGatewayRoute
     * @param page Page
     * @return IPage
     */
    IPage<SystemGatewayRoute> page(SystemGatewayRoute route, Page<SystemGatewayRoute> page);

    /**
     * 通过Id查询路由信息详情
     * @param id 业务主键
     * @return 对象
     */
    SystemGatewayRoute get(String id);

    /**
     * 通过Id路由信息
     * @param id 业务主键
     * @return Boolean
     */
    Boolean delete(String id);

    /**
     * 创建数据
     * @param route 要创建的对象
     * @return Boolean
     */
    String create(SystemGatewayRoute route);

    /**
     * 更新数据（必须带Code）
     * @param route 对象
     * @return Boolean
     */
    Boolean update(SystemGatewayRoute route);

    /**
     * 修改状态
     * @param route SystemGatewayRoute
     * @return Boolean
     */
    Boolean changeStatus(SystemGatewayRoute route);

}
