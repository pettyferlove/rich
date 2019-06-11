package com.github.rich.log.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.rich.common.core.utils.CommonUtils;
import com.github.rich.log.constants.LogKafkaTopicConstant;
import com.github.rich.log.dto.AuthLog;
import com.github.rich.log.service.AuthLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;

import static java.util.concurrent.Executors.newFixedThreadPool;

/**
 * @author Petty
 */
@Slf4j
@Service
public class AuthLogServiceImpl implements AuthLogService {

    private final ExecutorService executorService = newFixedThreadPool(2 * Runtime.getRuntime().availableProcessors() + 1);

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public void sendLoginLog(String userId, String loginName, String loginType) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert requestAttributes != null;
        HttpServletRequest request = requestAttributes.getRequest();
        String ip = CommonUtils.getIpAdrress(request);
        String userAgent = request.getHeader("user-agent");
        String method = request.getMethod();
        AuthLog log = new AuthLog();
        log.setLoginIp(ip);
        log.setUserId(userId);
        log.setUserAgent(userAgent);
        log.setLoginName(loginName);
        log.setLoginType(loginType);
        log.setRequestMethod(method);
        log.setLoginTime(LocalDateTime.now());
        executorService.execute(() -> kafkaTemplate.send(LogKafkaTopicConstant.AUTH_LOG_TOPIC, JSON.toJSONString(log)));
    }
}
