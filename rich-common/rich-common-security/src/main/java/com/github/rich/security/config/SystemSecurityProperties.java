package com.github.rich.security.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author Petty
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Configuration
@RefreshScope
@ConditionalOnExpression("!'${system.security}'.isEmpty()")
@ConfigurationProperties(prefix = "system.security")
public class SystemSecurityProperties {
    private String adminName;
    private String adminPassword;
    private List<String> authorities;
}
