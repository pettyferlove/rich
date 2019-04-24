package com.github.rich.message.service;

import com.github.rich.common.core.model.Message;

/**
 * @author Petty
 */
public interface IMessageService {

    /**
     * 消息发送
     * @param message 消息封装类
     * @return Boolean
     */
    boolean send(Message message);
}
