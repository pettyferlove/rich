package com.github.rich.base;

import com.github.rich.common.core.annotation.EnableSwaggerDoc;
import com.github.rich.security.annotation.EnableRichResourceServer;
import feign.Logger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

/**
 * @author Petty
 */
@Slf4j
@EnableRichResourceServer
@SpringCloudApplication
@EnableSwaggerDoc
@EnableScheduling
public class RichBaseInfoServiceApplication {

    /**
     * 负载均衡配置
     * @return RestTemplate
     */
    @Bean
    @LoadBalanced
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(RichBaseInfoServiceApplication.class, args);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public PasswordEncoder userPasswordEncoder() {
        return new MessageDigestPasswordEncoder("SHA-256");
    }

    /**
     * Feign 日志模式
     *
     * @return Logger.Level
     */
    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.NONE;
    }
}
