package com.github.rich.message.service;

import com.github.rich.message.domain.dto.Message;

/**
 * @author Petty
 */
public interface ISmsService<T extends Message> extends IMessageService<T> {
}
