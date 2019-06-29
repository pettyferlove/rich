package com.github.rich.message.listener;

import com.github.rich.message.dto.message.CaptchaMessage;
import com.github.rich.message.stream.sink.CaptchaSmsSink;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

/**
 * @author Petty
 */
@Slf4j
@Component
@EnableBinding(CaptchaSmsSink.class)
public class CaptchaMessageSmsListener {
    @StreamListener(CaptchaSmsSink.INPUT)
    public void handle(CaptchaMessage captchaMessage) {
        System.out.println("Received: " + captchaMessage);
    }
}
