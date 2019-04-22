package com.github.rich.message.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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
@ConfigurationProperties(prefix = "spring.rabbitmq")
public class RabbitMqCustomConfig {

    @Value("${spring.rabbitmq.retry:10}")
    Integer retry;

}
