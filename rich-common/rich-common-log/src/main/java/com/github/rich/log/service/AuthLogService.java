package com.github.rich.log.service;

/**
 * 授权日志
 * @author Petty
 */
public interface AuthLogService {

    /**
     * 保存登录日志
     *
     * @param userId  登录名
     * @param userName  用户实际名
     * @param loginType 登录类型
     */
    void sendLoginLog(String userId, String userName, String loginType);

}
