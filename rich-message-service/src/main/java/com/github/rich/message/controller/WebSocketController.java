package com.github.rich.message.controller;

import com.github.rich.message.vo.ClientMessage;
import com.github.rich.message.vo.ServerMessage;
import com.github.rich.security.service.impl.UserDetailsImpl;
import com.github.rich.security.utils.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.messaging.support.ExecutorSubscribableChannel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
        UserDetailsImpl user = SecurityUtil.getUser();
        simpMessagingTemplate.convertAndSendToUser(principal.getName(),
                "/topic/subscribe", new ServerMessage(message.getName())
        );
    }

    @SubscribeMapping("/subscribe")
    public ServerMessage sub(Principal principal) {
        String name = principal.getName();
        UserDetailsImpl user = SecurityUtil.getUser();
        return new ServerMessage("welcome");
    }

    @GetMapping("demo/{msg}")
    public void demo(Principal principal, @PathVariable String msg){
        simpMessagingTemplate.convertAndSendToUser(principal.getName(),
                "/topic/subscribe", new ServerMessage(msg)
        );
    }
}
