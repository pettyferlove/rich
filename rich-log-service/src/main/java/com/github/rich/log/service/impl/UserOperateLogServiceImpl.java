package com.github.rich.log.service.impl;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.rich.base.dto.UserDetailDTO;
import com.github.rich.base.feign.RemoteUserService;
import com.github.rich.common.core.utils.ConverterUtil;
import com.github.rich.log.constants.LogKafkaTopicConstant;
import com.github.rich.log.dto.OperateLogInfo;
import com.github.rich.log.entity.UserOperateLog;
import com.github.rich.log.mapper.UserOperateLogMapper;
import com.github.rich.log.service.IUserOperateLogService;
import com.github.rich.log.vo.UserLogVO;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
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

    private final RemoteUserService remoteUserService;

    public UserOperateLogServiceImpl(RemoteUserService remoteUserService) {
        this.remoteUserService = remoteUserService;
    }

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

    @Override
    public IPage<UserLogVO> page(UserOperateLog userOperateLog, Page<UserOperateLog> page) {
        IPage<UserLogVO> userLogPage = new Page<>();
        IPage<UserOperateLog> userOperateLogPage = this.page(page, Wrappers.lambdaQuery(userOperateLog).orderByDesc(UserOperateLog::getCreateTime));
        List<UserOperateLog> records = userOperateLogPage.getRecords();
        List<UserLogVO> nrecords = new LinkedList<>();
        for (UserOperateLog oldLog : records) {
            UserLogVO userLogVO = ConverterUtil.convert(oldLog, new UserLogVO());
            UserDetailDTO userDetail = remoteUserService.getUserDetail(oldLog.getUserId());
            userLogVO.setUserName(userDetail.getUserName());
            nrecords.add(userLogVO);
        }
        userLogPage.setCurrent(userOperateLogPage.getCurrent());
        userLogPage.setPages(userOperateLogPage.getPages());
        userLogPage.setSize(userOperateLogPage.getSize());
        userLogPage.setTotal(userOperateLogPage.getTotal());
        userLogPage.setRecords(nrecords);
        return userLogPage;
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
