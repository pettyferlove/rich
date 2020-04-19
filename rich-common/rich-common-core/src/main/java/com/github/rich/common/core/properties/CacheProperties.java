package com.github.rich.common.core.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

/**
 * @author Petty
 */
@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
@Configuration
@EnableCaching(proxyTargetClass = true)
@RefreshScope
@ConfigurationProperties(prefix = "spring.cache")
public class CacheProperties {

    /**
     * 缓存过期时间
     */
    @Value("${spring.cache.expire:0}")
    private Long expiration;
}
