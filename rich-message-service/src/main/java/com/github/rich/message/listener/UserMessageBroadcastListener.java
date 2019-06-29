package com.github.rich.message.listener;

import com.github.rich.common.core.utils.ConverterUtil;
import com.github.rich.message.dto.message.UserGeneralMessage;
import com.github.rich.message.stream.sink.UserMessageBroadcastSink;
import com.github.rich.message.vo.base.ServerMessage;
import com.github.rich.message.vo.message.UserMessageVO;
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
@EnableBinding(UserMessageBroadcastSink.class)
public class UserMessageBroadcastListener {

    private final SimpMessagingTemplate simpMessagingTemplate;

    public UserMessageBroadcastListener(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }


    @StreamListener(UserMessageBroadcastSink.INPUT)
    public void handle(UserGeneralMessage message) {
        simpMessagingTemplate.convertAndSendToUser(message.getReceiver(), "/topic/subscribe", new ServerMessage<>(ConverterUtil.convert(message, new UserMessageVO())));
    }

}
