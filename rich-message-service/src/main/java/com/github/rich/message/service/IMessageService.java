package com.github.rich.message.service;

import com.github.rich.common.core.model.Message;

/**
 * @author Petty
 */
public interface IMessageService {

    /**
     * 消息发送
     * @return
     */
    boolean send(Message message);
}
