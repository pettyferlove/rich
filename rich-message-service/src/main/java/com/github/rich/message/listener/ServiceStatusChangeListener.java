package com.github.rich.message.listener;

import com.github.rich.message.dto.message.ServiceStatusChangeEmailMessage;
import com.github.rich.message.stream.ServiceMonitorSink;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

/**
 * @author Petty
 */
@Slf4j
@Component
@EnableBinding(ServiceMonitorSink.class)
public class ServiceStatusChangeListener {
    @StreamListener(ServiceMonitorSink.INPUT)
    public void handle(ServiceStatusChangeEmailMessage serviceStatusChangeEmailMessage) {
        System.out.println("Received: " + serviceStatusChangeEmailMessage);
    }
}
