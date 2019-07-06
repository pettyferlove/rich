package com.github.rich.auth;

import com.github.rich.common.core.annotation.EnableRichFeignClients;
import feign.Logger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

/**
 * @author Petty
 */
@Slf4j
@SpringCloudApplication
@EnableRichFeignClients
public class RichAuthServiceApplication {

    /**
     * 负载均衡配置
     *
     * @return RestTemplate
     */
    @Bean
    @LoadBalanced
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    /**
     * 该RestTemplate不会调用Ribbon相关方法
     *
     * @return RestTemplate
     */
    @Bean(name = "remoteRestTemplate")
    public RestTemplate remoteRestTemplate() {
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(RichAuthServiceApplication.class, args);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
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
