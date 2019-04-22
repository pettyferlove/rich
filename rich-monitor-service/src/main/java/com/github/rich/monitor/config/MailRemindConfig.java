package com.github.rich.monitor.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

/**
 * @author Petty
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Configuration
@RefreshScope
@ConditionalOnExpression("!'${mail-remind}'.isEmpty()")
@ConfigurationProperties(prefix = "mail-remind")
public class MailRemindConfig {
    private String from;
    private String to;
    private long interval;
}
