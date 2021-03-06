package com.github.rich.message.controller;

import com.github.rich.common.core.constants.CommonConstant;
import com.github.rich.message.domain.vo.base.ClientMessage;
import com.github.rich.message.domain.vo.base.ServerMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @author Petty
 */
@Slf4j
@RestController
public class WebSocketController {

    private final SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    public WebSocketController(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @MessageMapping("/send")
    public void send(Principal principal, ClientMessage message) {
        simpMessagingTemplate.convertAndSendToUser(principal.getName(),
                "/topic/subscribe", new ServerMessage<>(message.getName())
        );
    }

    @SubscribeMapping("/subscribe")
    public ServerMessage subscribe(Principal principal) {
        return new ServerMessage<>(CommonConstant.WS_WELCOME, "welcome " + principal.getName());
    }
}
