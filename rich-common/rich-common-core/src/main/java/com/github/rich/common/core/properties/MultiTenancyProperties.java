package com.github.rich.common.core.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Petty
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Configuration
@RefreshScope
@ConditionalOnExpression("!'${multi-tenancy}'.isEmpty()")
@ConfigurationProperties(prefix = "multi-tenancy")
public class MultiTenancyProperties {

    String tableField;

    IgnoreObject ignore;


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class IgnoreObject {
        private List<String> roles = new ArrayList<>();
        private List<String> tables = new ArrayList<>();
    }
}
