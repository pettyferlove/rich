package com.github.rich.log.service.impl;

import com.github.rich.log.constants.LogKafkaTopicConstant;
import com.github.rich.log.entity.UserOperateLog;
import com.github.rich.log.mapper.UserOperateLogMapper;
import com.github.rich.log.service.IUserOperateLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Petty
 * @since 2019-06-12
 */
@Service
public class UserOperateLogServiceImpl extends ServiceImpl<UserOperateLogMapper, UserOperateLog> implements IUserOperateLogService {

    @Override
    @KafkaListener(topics = {LogKafkaTopicConstant.USER_OPERATE_LOG_TOPIC})
    public void receiveMessage(String message) {

    }
}
