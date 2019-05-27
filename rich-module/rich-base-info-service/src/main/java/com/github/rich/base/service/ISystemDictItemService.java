package com.github.rich.base.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.rich.base.entity.SystemDictItem;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Petty
 * @since 2019-05-27
 */
public interface ISystemDictItemService extends IService<SystemDictItem> {
    /**
     * List查找
     * @param page Page分页对象
     * @return IPage 返回结果
     */
    IPage<SystemDictItem> page(Page<SystemDictItem> page);

    /**
     * 通过Code查询DictItem信息
     * @param code 业务主键
     * @return 对象
     */
    SystemDictItem get(String code);

    /**
     * 通过Code删除信息
     * @param code 业务主键
     * @return Boolean
     */
    Boolean delete(String code);

    /**
     * 创建数据
     * @param dictItem 要创建的对象
     * @return Boolean
     */
    String create(SystemDictItem dictItem);

    /**
     * 更新数据（必须带Code）
     * @param dictItem 对象
     * @return Boolean
     */
    Boolean update(SystemDictItem dictItem);
}
