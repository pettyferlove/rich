package com.github.rich.message.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

/**
 * 附件配置（阿里云）
 * @author Petty
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Configuration
@RefreshScope
@ConfigurationProperties(prefix = "sms.cloud.aliyun")
public class SmsAliyunProperties {
    private String signName;
    private String accessKeyId;
    private String accessKeySecret;
}
