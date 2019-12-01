package com.github.rich.gateway;

import com.github.rich.common.core.annotation.EnableRichFeignClients;
import feign.Logger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @author Petty
 */
@Slf4j
@SpringCloudApplication
@EnableRichFeignClients
public class RichGatewayServiceApplication {

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
     * 实例化一个转换器，默认的实例化必须在Web环境非ReactiveWeb环境下才能被实例化，
     *
     * @return HttpMessageConverters
     * @see {@link org.springframework.boot.autoconfigure.http.HttpMessageConvertersAutoConfiguration}
     */
    @Bean
    public HttpMessageConverters httpMessageConverters() {
        return new HttpMessageConverters();
    }

    public static void main(String[] args) {
        SpringApplication.run(RichGatewayServiceApplication.class, args);
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
