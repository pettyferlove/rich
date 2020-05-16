package com.github.rich.base.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.rich.base.entity.SystemDictType;
import com.github.rich.base.domain.vo.Dict;

import java.util.List;

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
     * 根据字典类型查询字典项
     *
     * @param type 字典类型 eg: user_type
     * @return Dict集合
     */
    List<Dict> list(String type);

    /**
     * List查找
     *
     * @param dictType 查询参数对象
     * @param page     Page分页对象
     * @return IPage 返回结果
     */
    IPage<SystemDictType> page(SystemDictType dictType, Page<SystemDictType> page);

    /**
     * 通过Id查询DictType信息
     *
     * @param id 业务主键
     * @return 对象
     */
    SystemDictType get(String id);

    /**
     * 通过Id删除信息
     *
     * @param id 业务主键
     * @return Integer 2 存在子项 1 删除成功 0 删除失败
     */
    Integer delete(String id);

    /**
     * 创建数据
     *
     * @param userId   UserId
     * @param dictType 要创建的对象
     * @return Boolean
     */
    String create(String userId, SystemDictType dictType);

    /**
     * 更新数据（必须带Code）
     *
     * @param userId   UserId
     * @param dictType 对象
     * @return Boolean
     */
    Boolean update(String userId, SystemDictType dictType);

    /**
     * 检查字典类型是否存在
     *
     * @param type 类型（CODE）
     * @return 存在True 不存在False
     */
    Boolean check(String type);
}
