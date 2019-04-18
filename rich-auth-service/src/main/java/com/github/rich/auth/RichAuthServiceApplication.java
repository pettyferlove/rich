package com.github.rich.auth;

import com.github.rich.common.core.annotation.EnableRichFeignClients;
import feign.Logger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
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
     * @return RestTemplate
     */
    @Bean
    @LoadBalanced
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(RichAuthServiceApplication.class, args);
    }

    /**
     * 开启Feign Debug模式
     * @return Logger.Level
     */
    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }
}
