package com.github.rich.gateway;

import com.github.rich.common.core.annotation.EnableRichFeignClients;
import feign.Logger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @author Petty
 */
@Slf4j
@EnableRichFeignClients
@SpringCloudApplication
public class RichGatewayServiceApplication {

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
