package com.github.rich.message.listener;

import com.github.rich.message.dto.message.ServiceStatusChangeEmailMessage;
import com.github.rich.message.stream.ServiceMonitorProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

/**
 * @author Petty
 */
@Slf4j
@Component
@EnableBinding(ServiceMonitorProcessor.class)
public class ServiceStatusChangeListener {
    @StreamListener(ServiceMonitorProcessor.INPUT)
    public void handle(ServiceStatusChangeEmailMessage serviceStatusChangeEmailMessage) {
        System.out.println("Received: " + serviceStatusChangeEmailMessage);
    }
}
