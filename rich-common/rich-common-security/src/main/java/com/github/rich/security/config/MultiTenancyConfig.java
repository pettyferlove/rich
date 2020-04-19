package com.github.rich.security.config;

import com.baomidou.mybatisplus.extension.plugins.tenant.TenantSqlParser;
import com.github.rich.security.properties.MultiTenancyProperties;
import com.github.rich.security.tenant.RichTenantHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Petty
 */
@Slf4j
@Configuration
@ConditionalOnMissingBean(MultiTenancyProperties.class)
public class MultiTenancyConfig {

    private final MultiTenancyProperties multiTenancyProperties;

    public MultiTenancyConfig(MultiTenancyProperties multiTenancyProperties) {
        this.multiTenancyProperties = multiTenancyProperties;
    }

    @Bean
    public TenantSqlParser tenantSqlParser() {
        return new TenantSqlParser()
                .setTenantHandler(new RichTenantHandler(multiTenancyProperties));
    }

}
