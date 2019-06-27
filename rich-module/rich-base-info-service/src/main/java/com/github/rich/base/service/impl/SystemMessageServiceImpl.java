package com.github.rich.base.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.rich.base.entity.SystemMessage;
import com.github.rich.base.mapper.SystemMessageMapper;
import com.github.rich.base.service.ISystemMessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.github.rich.common.core.exception.BaseRuntimeException;
import com.github.rich.security.utils.SecurityUtil;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * <p>
 * 系统消息 服务实现类
 * </p>
 *
 * @author Petty
 * @since 2019-06-27
 */
@Service
public class SystemMessageServiceImpl extends ServiceImpl<SystemMessageMapper, SystemMessage> implements ISystemMessageService {

    @Override
    public IPage<SystemMessage> page(SystemMessage systemMessage, Page<SystemMessage> page) {
        return this.page(page, Wrappers.lambdaQuery(systemMessage).orderByDesc(SystemMessage::getCreateTime));
    }

    @Override
    public SystemMessage get(String id) {
        return this.getById(id);
    }

    @Override
    public Boolean delete(String id) {
        return this.removeById(id);
    }

    @Override
    public String create(SystemMessage systemMessage) {
        String systemMessageId = IdUtil.simpleUUID();
        systemMessage.setId(systemMessageId);
        systemMessage.setCreator(Objects.requireNonNull(SecurityUtil.getUser()).getUserId());
        systemMessage.setCreateTime(LocalDateTime.now());
        if (this.save(systemMessage)) {
            return systemMessageId;
        } else {
            throw new BaseRuntimeException("新增失败");
        }
    }

    @Override
    public Boolean update(SystemMessage systemMessage) {
        systemMessage.setModifier(Objects.requireNonNull(SecurityUtil.getUser()).getUserId());
        systemMessage.setModifierTime(LocalDateTime.now());
        return this.updateById(systemMessage);
    }

}
