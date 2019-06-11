package com.github.rich.log.service;

import com.github.rich.log.dto.OperateLogInfo;

/**
 * @author Petty
 */
public interface OperateLogService {

    /**
     * 发送日志
     * @param logInfo OperateLogInfo
     */
    void sendLog(OperateLogInfo logInfo);
}
