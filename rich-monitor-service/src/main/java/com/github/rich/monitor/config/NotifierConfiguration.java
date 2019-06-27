package com.github.rich.monitor.config;

import com.github.rich.monitor.notifier.ServiceStatusNotifier;
import com.github.rich.message.stream.MonitorProcessor;
import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Petty
 */
@Configuration
public class NotifierConfiguration {

    private final MailRemindProperties mailRemindProperties;

    private final MonitorProcessor processor;

    public NotifierConfiguration(MailRemindProperties mailRemindProperties, MonitorProcessor processor) {
        this.mailRemindProperties = mailRemindProperties;
        this.processor = processor;
    }

    @Bean
    public ServiceStatusNotifier serviceStatusNotifier(InstanceRepository repository) {
        return new ServiceStatusNotifier(repository, processor, mailRemindProperties);
    }

}
