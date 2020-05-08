package com.github.rich.security.annotation;

import com.github.rich.security.component.RichResourceServerAutoConfiguration;
import com.github.rich.security.component.RichResourceServerConfigurerAdapter;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

import java.lang.annotation.*;

/**
 * 初始化资源服务
 * 该注解将会把服务变成OAuth2服务系统中的资源服务，会忽略Spring Security自身部分功能
 *
 * @author Petty
 * @version 1.0.0
 * @since 如果应用需要自己实现登录等操作，则不要使用该注解
 */
@Documented
@Inherited
@EnableResourceServer
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableFeignClients(clients = {com.github.rich.base.feign.UserServiceFeignClient.class})
@Import({RichResourceServerAutoConfiguration.class, RichResourceServerConfigurerAdapter.class})
public @interface EnableRichResourceServer {
}
