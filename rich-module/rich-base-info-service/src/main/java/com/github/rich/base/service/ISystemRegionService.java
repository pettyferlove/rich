package com.github.rich.base.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.rich.base.entity.SystemRegion;
import com.github.rich.base.vo.RegionNode;

import java.util.List;

/**
 * <p>
 * 行政区划信息表 服务类
 * </p>
 *
 * @author Petty
 * @since 2019-06-26
 */
public interface ISystemRegionService extends IService<SystemRegion> {

    /**
     * 加载区划树（完整加载）
     *
     * @return RegionNode集合
     */
    List<RegionNode> loadTree();

    /**
     * 更绝ID加载所属子节点
     *
     * @param id id
     * @return SystemRegion集合
     */
    List<SystemRegion> loadNodes(String id);

    /**
     * 通过Id查询SystemRegion信息
     *
     * @param id 业务主键
     * @return 对象
     */
    SystemRegion get(String id);

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
     * @param userId       userId
     * @param systemRegion 要创建的对象
     * @return Boolean
     */
    String create(String userId, SystemRegion systemRegion);

    /**
     * 更新数据（必须带Id）
     *
     * @param userId       userId
     * @param systemRegion 对象
     * @return Boolean
     */
    Boolean update(String userId, SystemRegion systemRegion);

    /**
     * 检查区域编号是否存在
     *
     * @param region 区域编号（CODE）
     * @return 存在True 不存在False
     */
    Boolean check(String region);

}
