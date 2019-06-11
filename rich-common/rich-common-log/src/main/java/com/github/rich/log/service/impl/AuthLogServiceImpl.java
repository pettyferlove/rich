package com.github.rich.log.service.impl;

import com.github.rich.log.service.AuthLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author Petty
 */
@Slf4j
@Service
public class AuthLogServiceImpl implements AuthLogService {
    @Override
    public void sendLoginLog(String userId, String loginName, String loginType) {
        log.info("UserID:{},LoginName:{},LoginType:{}",userId,loginName,loginType);
    }
}
