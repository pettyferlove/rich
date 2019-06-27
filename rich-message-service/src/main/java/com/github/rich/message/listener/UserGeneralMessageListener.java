package com.github.rich.message.listener;

import com.github.rich.common.core.dto.message.UserGeneralMessage;
import com.github.rich.common.core.stream.UserMessageProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

/**
 * @author Petty
 */
@Slf4j
@Component
@EnableBinding(UserMessageProcessor.class)
public class UserGeneralMessageListener {
    @StreamListener(UserMessageProcessor.USER_GENERAL_MESSAGE_INPUT)
    public void handle(UserGeneralMessage message) {
        System.out.println(message);
    }
}
