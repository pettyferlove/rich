package com.github.rich.auth.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

/**
 * 配置验证码过滤器是否验证
 * @author Petty
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Configuration
@RefreshScope
@EnableConfigurationProperties(CaptchaFilterConfig.class)
@ConfigurationProperties(prefix = "captcha")
public class CaptchaFilterConfig {
    /**
     * 是否开启Password模式验证码校验
     */
    @Value("${captcha.password:false}")
    Boolean password;

    /**
     * 是否开启Mobile模式验证码校验
     */
    @Value("${captcha.password:true}")
    Boolean mobile;

}
