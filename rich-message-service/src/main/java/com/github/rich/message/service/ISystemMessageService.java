package com.github.rich.message.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.rich.message.dto.message.UserGeneralMessage;
import com.github.rich.message.entity.SystemMessage;

import java.util.List;

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
     * @return List 返回结果
     */
    List<UserGeneralMessage> load();

    /**
     * 创建数据
     *
     * @param systemMessage 要创建的对象
     * @return Boolean
     */
    String create(SystemMessage systemMessage);

    /**
     * 变更状态为已读
     *
     * @param id 消息ID
     * @return Boolean
     */
    Boolean read(String id);

}
