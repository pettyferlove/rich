package com.github.rich.log.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.rich.common.core.utils.ConverterUtil;
import com.github.rich.log.constants.LogKafkaTopicConstant;
import com.github.rich.log.domain.dto.OperateLogInfo;
import com.github.rich.log.entity.UserOperateLog;
import com.github.rich.log.mapper.UserOperateLogMapper;
import com.github.rich.log.service.IUserOperateLogService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
            userOperateLog.setCreateTime(LocalDateTime.now());
            this.save(userOperateLog);
        }
    }

    @Override
    public IPage<UserOperateLog> page(UserOperateLog userOperateLog, Page<UserOperateLog> page) {
        return this.page(page, Wrappers.lambdaQuery(userOperateLog).orderByDesc(UserOperateLog::getCreateTime));
    }

    @Override
    public UserOperateLog get(String id) {
        return this.getById(id);
    }

    @Override
    public Boolean delete(String id) {
        return this.removeById(id);
    }
}
