package com.github.rich.log.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.rich.log.entity.UserOperateLog;

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

    /**
     * 查询日志列表
     * @param userOperateLog UserLog
     * @param page Page
     * @return IPage
     */
    IPage<UserOperateLog> page(UserOperateLog userOperateLog, Page<UserOperateLog> page);

    /**
     * 通过Id查询日志详情
     * @param id 业务主键
     * @return 对象
     */
    UserOperateLog get(String id);

    /**
     * 通过Id日志信息
     * @param id 业务主键
     * @return Boolean
     */
    Boolean delete(String id);

}
