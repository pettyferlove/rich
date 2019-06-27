package com.github.rich.message.listener;

import com.github.rich.message.dto.message.UserGeneralMessage;
import com.github.rich.message.service.ISystemMessageService;
import com.github.rich.message.stream.UserMessageProcessor;
import com.github.rich.message.vo.ServerMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

/**
 * @author Petty
 */
@Slf4j
@Component
@EnableBinding(UserMessageProcessor.class)
public class UserGeneralMessageListener {

    private final ISystemMessageService systemMessageService;

    private final SimpMessagingTemplate simpMessagingTemplate;

    public UserGeneralMessageListener(ISystemMessageService systemMessageService, SimpMessagingTemplate simpMessagingTemplate) {
        this.systemMessageService = systemMessageService;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @StreamListener(UserMessageProcessor.USER_GENERAL_MESSAGE_INPUT)
    public void handle(UserGeneralMessage message) {
        System.out.println(message);
        simpMessagingTemplate.convertAndSendToUser(message.getReceiver(), "/topic/subscribe", new ServerMessage(message.getMessage(), message.getLevel(), message.getTime()));
    }
}
