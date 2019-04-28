package com.github.rich.thirdparty;

import com.github.rich.common.core.annotation.EnableRichFeignClients;
import com.github.rich.common.core.annotation.EnableSwaggerDoc;
import com.github.rich.security.annotation.EnableRichResourceServer;
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
@EnableSwaggerDoc
@EnableRichFeignClients
@EnableRichResourceServer
@SpringCloudApplication
public class RichThirdPartyServiceApplication {

    /**
     * 负载均衡配置
     * @return RestTemplate
     */
    @Bean
    @LoadBalanced
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    /**
     * 该RestTemplate不会调用Ribbon相关方法
     * @return RestTemplate
     */
    @Bean(name="remoteRestTemplate")
    public RestTemplate remoteRestTemplate() {
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(RichThirdPartyServiceApplication.class, args);
    }

}
