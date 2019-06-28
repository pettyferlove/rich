package com.github.rich.message.listener;

import com.github.rich.base.feign.RemoteUserService;
import com.github.rich.common.core.constants.CommonConstant;
import com.github.rich.common.core.utils.ConverterUtil;
import com.github.rich.message.dto.message.UserGeneralMessage;
import com.github.rich.message.entity.SystemMessage;
import com.github.rich.message.service.ISystemMessageService;
import com.github.rich.message.stream.UserMessageProcessor;
import com.github.rich.message.vo.ServerMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author Petty
 */
@Slf4j
@Component
@EnableBinding(UserMessageProcessor.class)
public class UserGeneralMessageListener {

    private final ISystemMessageService systemMessageService;

    private final SimpMessagingTemplate simpMessagingTemplate;

    private final RemoteUserService remoteUserService;

    public UserGeneralMessageListener(ISystemMessageService systemMessageService, SimpMessagingTemplate simpMessagingTemplate, RemoteUserService remoteUserService) {
        this.systemMessageService = systemMessageService;
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.remoteUserService = remoteUserService;
    }

    @StreamListener(UserMessageProcessor.USER_GENERAL_MESSAGE_INPUT)
    public void handle(UserGeneralMessage message) {
        SystemMessage systemMessage = ConverterUtil.convert(message, new SystemMessage());
        systemMessage.setCreator(CommonConstant.SYSTEM_USER_ID);
        systemMessage.setCreateTime(LocalDateTime.now());
        if(!CommonConstant.SYSTEM_USER_ID.equals(message.getDeliver())){
            message.setAvatar(remoteUserService.getUserDetail(message.getDeliver()).getUserAvatar());
        }
        try{
            String id = systemMessageService.create(systemMessage);
            message.setId(id);
            simpMessagingTemplate.convertAndSendToUser(message.getReceiver(), "/topic/subscribe", new ServerMessage<>(message));
        }catch (Exception e){
            throw new RuntimeException("持久化消息失败");
        }
    }
}
