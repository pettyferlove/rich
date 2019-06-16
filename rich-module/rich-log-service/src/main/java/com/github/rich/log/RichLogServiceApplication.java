package com.github.rich.log;

import com.github.rich.common.core.annotation.EnableRichFeignClients;
import com.github.rich.common.core.annotation.EnableSwaggerDoc;
import com.github.rich.log.constants.LogKafkaTopicConstant;
import com.github.rich.security.annotation.EnableRichResourceServer;
import feign.Logger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.client.RestTemplate;

/**
 * @author Petty
 */
@Slf4j
@EnableRichResourceServer
@EnableRichFeignClients
@SpringCloudApplication
@EnableSwaggerDoc
public class RichLogServiceApplication {

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

    public static void main(String[] args) {
        SpringApplication.run(RichLogServiceApplication.class, args);
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
