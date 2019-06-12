package com.github.rich.log.service.impl;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSONObject;
import com.github.rich.common.core.utils.ConverterUtil;
import com.github.rich.log.constants.LogKafkaTopicConstant;
import com.github.rich.log.dto.OperateLogInfo;
import com.github.rich.log.entity.UserOperateLog;
import com.github.rich.log.mapper.UserOperateLogMapper;
import com.github.rich.log.service.IUserOperateLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * <p>
 * 服务实现类
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
        OperateLogInfo operateLogInfos = JSONObject.parseObject(message, OperateLogInfo.class);
        Optional<UserOperateLog> convert = Optional.ofNullable(ConverterUtil.convert(operateLogInfos, new UserOperateLog()));
        if (convert.isPresent()) {
            UserOperateLog userOperateLog = convert.get();
            userOperateLog.setId(IdUtil.simpleUUID());
            userOperateLog.setCreateTime(LocalDateTime.now());
            this.save(userOperateLog);
        }
    }
}
