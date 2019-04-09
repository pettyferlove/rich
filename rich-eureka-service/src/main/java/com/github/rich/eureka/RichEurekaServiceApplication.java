package com.github.rich.eureka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * 服务注册中心
 * @author Petty
 */
@Slf4j
@EnableEurekaServer
@SpringBootApplication
public class RichEurekaServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(RichEurekaServiceApplication.class, args);
    }

    /**
     * 负载均衡配置
     * @return RestTemplate
     */
    @Bean
    @LoadBalanced
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
