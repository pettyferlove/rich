package com.github.rich.message.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.rich.message.entity.SystemMessage;
import com.github.rich.message.vo.message.UserMessageVO;

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
    List<UserMessageVO> loadUnread();

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
