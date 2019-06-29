package com.github.rich.monitor.config;

import com.github.rich.message.stream.ServiceMonitorSource;
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

    private final ServiceMonitorSource source;

    public NotifierConfiguration(MailRemindProperties mailRemindProperties, ServiceMonitorSource source) {
        this.mailRemindProperties = mailRemindProperties;
        this.source = source;
    }

    @Bean
    public ServiceStatusNotifier serviceStatusNotifier(InstanceRepository repository) {
        return new ServiceStatusNotifier(repository, source, mailRemindProperties);
    }

}
