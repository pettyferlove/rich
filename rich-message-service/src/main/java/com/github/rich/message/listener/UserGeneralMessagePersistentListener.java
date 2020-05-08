package com.github.rich.message.listener;

import com.github.rich.base.feign.UserServiceFeignClient;
import com.github.rich.common.core.constants.CommonConstant;
import com.github.rich.common.core.utils.ConverterUtil;
import com.github.rich.message.dto.message.UserGeneralMessage;
import com.github.rich.message.entity.SystemMessage;
import com.github.rich.message.service.ISystemMessageService;
import com.github.rich.message.stream.source.UserMessageBroadcastSource;
import com.github.rich.message.stream.sink.UserMessageSink;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author Petty
 */
@Slf4j
@Component
@EnableBinding({UserMessageSink.class, UserMessageBroadcastSource.class})
public class UserGeneralMessagePersistentListener {

    private final ISystemMessageService systemMessageService;

    private final UserServiceFeignClient userServiceFeignClient;

    public UserGeneralMessagePersistentListener(ISystemMessageService systemMessageService, UserServiceFeignClient userServiceFeignClient) {
        this.systemMessageService = systemMessageService;
        this.userServiceFeignClient = userServiceFeignClient;
    }

    /**
     * 多实例情况下用户订阅哪一个服务未知，持久化之后将消息广播至多个实例，进行消息推送
     *
     * @param message UserGeneralMessage
     * @return UserGeneralMessage
     */
    @StreamListener(UserMessageSink.INPUT)
    @SendTo(UserMessageBroadcastSource.OUTPUT)
    public UserGeneralMessage handle(UserGeneralMessage message) {
        SystemMessage systemMessage = ConverterUtil.convert(message, new SystemMessage());
        systemMessage.setCreator(CommonConstant.SYSTEM_USER_ID);
        systemMessage.setCreateTime(LocalDateTime.now());
        if (!CommonConstant.SYSTEM_USER_ID.equals(message.getDeliver())) {
            message.setAvatar(userServiceFeignClient.getUserDetail(message.getDeliver()).getUserAvatar());
        }
        try {
            String id = systemMessageService.create(systemMessage);
            message.setId(id);
            return message;
        } catch (Exception e) {
            throw new RuntimeException("持久化消息失败");
        }
    }
}
