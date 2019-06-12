package com.github.rich.log.service;

import com.github.rich.log.entity.UserOperateLog;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Petty
 * @since 2019-06-12
 */
public interface IUserOperateLogService extends IService<UserOperateLog> {

    /**
     * 接受Kafka消息
     * @param message 消息
     */
    void receiveMessage(String message);
}
