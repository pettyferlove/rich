package com.github.rich.message.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.rich.message.entity.SystemMessage;

/**
 * <p>
 * 系统消息 服务类
 * </p>
 *
 * @author Petty
 * @since 2019-06-27
 */
public interface ISystemMessageService extends IService<SystemMessage> {

    /**
     * List查找
     *
     * @param systemMessage 查询参数对象
     * @param page          Page分页对象
     * @return IPage 返回结果
     */
    IPage<SystemMessage> page(SystemMessage systemMessage, Page<SystemMessage> page);

    /**
     * 通过Id查询SystemMessage信息
     *
     * @param id 业务主键
     * @return 对象
     */
    SystemMessage get(String id);

    /**
     * 创建数据
     *
     * @param systemMessage 要创建的对象
     * @return Boolean
     */
    Boolean create(SystemMessage systemMessage);

}
