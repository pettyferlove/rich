package com.github.rich.message.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.rich.message.entity.SystemMessage;
import com.github.rich.message.mapper.SystemMessageMapper;
import com.github.rich.message.service.ISystemMessageService;
import org.springframework.stereotype.Service;

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


}
