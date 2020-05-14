package com.github.rich.log.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.rich.log.constants.LogKafkaTopicConstant;
import com.github.rich.log.domain.dto.OperateLogInfo;
import com.github.rich.log.service.OperateLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;

import static java.util.concurrent.Executors.newFixedThreadPool;

/**
 * @author Petty
 */
@Service
public class OperateLogServiceImpl implements OperateLogService {

    private final ExecutorService executorService = newFixedThreadPool(2 * Runtime.getRuntime().availableProcessors() + 1);

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public void sendLog(OperateLogInfo logInfo) {
        executorService.execute(() -> kafkaTemplate.send(LogKafkaTopicConstant.USER_OPERATE_LOG_TOPIC, JSON.toJSONString(logInfo)));
    }
}
