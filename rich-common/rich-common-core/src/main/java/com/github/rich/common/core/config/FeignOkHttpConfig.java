package com.github.rich.common.core.config;

import com.github.rich.common.core.bean.interceptor.ServiceFeignInterceptor;
import feign.Feign;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * @author Petty
 * @date 2018/4/23
 */
@Configuration
@ConditionalOnClass(Feign.class)
@AutoConfigureBefore(FeignAutoConfiguration.class)
@ConditionalOnProperty(value = "feign.okhttp.enabled", matchIfMissing = true)
public class FeignOkHttpConfig {

    private final ServiceFeignInterceptor serviceFeignInterceptor;

    @Autowired
    public FeignOkHttpConfig(ServiceFeignInterceptor serviceFeignInterceptor) {
        this.serviceFeignInterceptor = serviceFeignInterceptor;
    }

    /**
     * 配置连接属性
     *
     * @return OkHttpClient
     */
    @Bean
    public OkHttpClient okHttpClient() {
        return new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .connectionPool(connectionPool())
                .addInterceptor(serviceFeignInterceptor)
                .build();
    }

    /**
     * 配置连接池最大空闲时间，最长连接时间
     *
     * @return ConnectionPool
     */
    @Bean
    public ConnectionPool connectionPool() {
        return new ConnectionPool(300, 10, TimeUnit.SECONDS);
    }
}
