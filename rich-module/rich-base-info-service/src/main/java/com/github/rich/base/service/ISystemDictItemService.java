package com.github.rich.base.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.rich.base.entity.SystemDictItem;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

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
     * @param typeId 字典类型Id
     * @return IPage 返回结果
     */
    IPage<SystemDictItem> page(String typeId, Page<SystemDictItem> page);

    /**
     * 通过Id查询DictItem信息
     * @param id 业务主键
     * @return 对象
     */
    SystemDictItem get(String id);

    /**
     * 通过Id删除信息
     * @param id 业务主键
     * @return Boolean
     */
    Boolean delete(String id);

    /**
     * 通过ids批量删除
     * @param ids id数组
     * @return Boolean
     */
    Boolean deleteByCodes(List<String> ids);

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
