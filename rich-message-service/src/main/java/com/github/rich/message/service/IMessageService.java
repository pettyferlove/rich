package com.github.rich.message.service;

import com.github.rich.message.domain.dto.Message;

/**
 * @author Petty
 */
public interface IMessageService<T extends Message> {

    /**
     * 消息发送
     * @param message 消息封装类
     * @return Boolean
     */
    boolean send(T message);
}
