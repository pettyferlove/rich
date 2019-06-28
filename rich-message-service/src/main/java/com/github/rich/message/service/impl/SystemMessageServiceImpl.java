package com.github.rich.message.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.rich.common.core.exception.BaseRuntimeException;
import com.github.rich.common.core.utils.ConverterUtil;
import com.github.rich.message.dto.message.UserGeneralMessage;
import com.github.rich.message.entity.SystemMessage;
import com.github.rich.message.mapper.SystemMessageMapper;
import com.github.rich.message.service.ISystemMessageService;
import com.github.rich.security.utils.SecurityUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
    public List<UserGeneralMessage> load() {
        List<SystemMessage> list = this.list(Wrappers.<SystemMessage>lambdaQuery().orderByDesc(SystemMessage::getCreateTime).eq(SystemMessage::getReceiver, Objects.requireNonNull(SecurityUtil.getUser()).getUserId()));
        Optional<List<UserGeneralMessage>> optionalUserGeneralMessages = Optional.ofNullable(ConverterUtil.convertList(SystemMessage.class, UserGeneralMessage.class, list));
        return optionalUserGeneralMessages.orElseGet(ArrayList::new);
    }

    @Override
    public String create(SystemMessage systemMessage) {
        String systemMessageId = IdUtil.simpleUUID();
        systemMessage.setId(systemMessageId);
        if (this.save(systemMessage)) {
            return systemMessageId;
        } else {
            throw new BaseRuntimeException("新增失败");
        }
    }

    @Override
    public Boolean read(String id) {
        SystemMessage systemMessage = new SystemMessage();
        systemMessage.setId(id);
        systemMessage.setState(1);
        return this.updateById(systemMessage);
    }


}
