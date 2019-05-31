package com.github.rich.base.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.rich.base.entity.SystemDictType;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 字典表 服务类
 * </p>
 *
 * @author Petty
 * @since 2019-05-27
 */
public interface ISystemDictTypeService extends IService<SystemDictType> {

    /**
     * List查找
     * @param dictType 查询参数对象
     * @param page Page分页对象
     * @return IPage 返回结果
     */
    IPage<SystemDictType> page(SystemDictType dictType, Page<SystemDictType> page);

    /**
     * 通过Id查询DictType信息
     * @param id 业务主键
     * @return 对象
     */
    SystemDictType get(String id);

    /**
     * 通过Id删除信息
     * @param id 业务主键
     * @return Boolean
     */
    Integer delete(String id);

    /**
     * 创建数据
     * @param dictType 要创建的对象
     * @return Boolean
     */
    String create(SystemDictType dictType);

    /**
     * 更新数据（必须带Code）
     * @param dictType 对象
     * @return Boolean
     */
    Boolean update(SystemDictType dictType);
}
