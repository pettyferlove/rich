package com.github.rich.security.component;

import com.github.rich.security.service.impl.RichRemoteTokenServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;


/**
 * 资源服务初始化过程中需要自定义或者初始化的Bean
 *
 * @author Petty
 */
@Slf4j
@ComponentScan({
        "com.github.rich.security.aop",
        "com.github.rich.security.component",
        "com.github.rich.security.feign",
        "com.github.rich.security.service",
})
public class RichResourceServerAutoConfiguration {

    private final ResourceServerProperties resource;

    public RichResourceServerAutoConfiguration(ResourceServerProperties resource) {
        this.resource = resource;
    }

    /**
     * 自定义RestTemplate 增加错误处理以及日志打印
     *
     * @return RestTemplate
     */
    @Bean
    @Primary
    @LoadBalanced
    public RestTemplate richRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler() {
            @Override
            public void handleError(ClientHttpResponse response) {
                try {
                    if (response.getRawStatusCode() != HttpStatus.BAD_REQUEST.value()) {
                        super.handleError(response);
                    }
                } catch (Exception e) {
                    log.error("RestTemplate richRestTemplate->Error Response:{}", response);
                }
            }
        });
        return restTemplate;
    }

    @Bean
    @Primary
    public RichRemoteTokenServices richRemoteTokenServices() {
        RichRemoteTokenServices services = new RichRemoteTokenServices();
        services.setCheckTokenEndpointUrl(this.resource.getTokenInfoUri());
        services.setClientId(this.resource.getClientId());
        services.setClientSecret(this.resource.getClientSecret());
        return services;
    }
}
