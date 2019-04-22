package com.github.rich.message.listener;

import com.github.rich.common.core.constant.MqQueueConstant;
import com.github.rich.common.core.exception.BaseException;
import com.github.rich.common.core.model.message.CaptchaMessage;
import com.github.rich.message.service.ISmsService;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author Petty
 */
@Slf4j
@Component
@RabbitListener(queues = MqQueueConstant.SERVICE_SMS_QUEUE)
public class CaptchaMessageSmsListener {

    private final ISmsService smsService;

    public CaptchaMessageSmsListener(ISmsService smsService) {
        this.smsService = smsService;
    }

    @RabbitHandler
    public void process(CaptchaMessage captchaMessage, Channel channel, Message message) {
        try {
            if (!smsService.send(captchaMessage)) {
                throw new BaseException("sms send error");
            } else {
                log.info("sms send success, time: {}", System.currentTimeMillis());
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            try {
                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

    }
}
