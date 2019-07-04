package com.github.rich.message.listener;

import com.github.rich.message.dto.message.CaptchaMessage;
import com.github.rich.message.service.IMessageService;
import com.github.rich.message.stream.sink.LoginCaptchaSmsSink;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

/**
 * @author Petty
 */
@Slf4j
@Component
@EnableBinding(LoginCaptchaSmsSink.class)
public class LoginCaptchaMessageSmsListener {

    private final IMessageService<CaptchaMessage> iMessageService;

    public LoginCaptchaMessageSmsListener(@Qualifier("loginCaptchaAliyunMessage") IMessageService<CaptchaMessage> iMessageService) {
        this.iMessageService = iMessageService;
    }

    @StreamListener(LoginCaptchaSmsSink.INPUT)
    public void handle(CaptchaMessage captchaMessage) {
        iMessageService.send(captchaMessage);
    }
}
