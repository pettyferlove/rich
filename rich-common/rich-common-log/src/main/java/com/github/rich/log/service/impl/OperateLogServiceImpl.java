package com.github.rich.log.service.impl;

import com.github.rich.log.domain.dto.OperateLogInfo;
import com.github.rich.log.service.OperateLogService;
import com.github.rich.log.stream.source.UserOperateLogSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.GenericMessage;

import java.util.concurrent.ExecutorService;

import static java.util.concurrent.Executors.newFixedThreadPool;

/**
 * @author Petty
 */
@EnableBinding({UserOperateLogSource.class})
public class OperateLogServiceImpl implements OperateLogService {

    private final ExecutorService executorService = newFixedThreadPool(2 * Runtime.getRuntime().availableProcessors() + 1);

    private final UserOperateLogSource source;

    @Autowired
    public OperateLogServiceImpl(UserOperateLogSource source) {
        this.source = source;
    }

    @Override
    public void sendLog(OperateLogInfo logInfo) {
        executorService.execute(() -> source.output().send(new GenericMessage<>(logInfo)));
    }
}
