package com.github.rich.wechat;

import com.github.rich.common.core.annotation.EnableRichFeignClients;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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
public class RichWeChatServiceApplication {

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
        SpringApplication.run(RichWeChatServiceApplication.class, args);
    }

}
