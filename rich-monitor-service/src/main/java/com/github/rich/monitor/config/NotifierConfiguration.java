package com.github.rich.monitor.config;

import com.github.rich.common.core.service.IMessageSender;
import com.github.rich.monitor.notifier.ServiceStatusNotifier;
import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Petty
 */
@Configuration
public class NotifierConfiguration {

    private final MailRemindProperties mailRemindProperties;

    private final IMessageSender messageSender;

    public NotifierConfiguration(MailRemindProperties mailRemindProperties, IMessageSender messageSender) {
        this.mailRemindProperties = mailRemindProperties;
        this.messageSender = messageSender;
    }

    @Bean
    public ServiceStatusNotifier serviceStatusNotifier(InstanceRepository repository) {
        return new ServiceStatusNotifier(repository, mailRemindProperties, messageSender);
    }

}
