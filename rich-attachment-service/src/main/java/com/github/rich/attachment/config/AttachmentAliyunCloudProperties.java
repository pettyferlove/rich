package com.github.rich.attachment.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

/**
 * 附件配置
 * @author Petty
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Configuration
@RefreshScope
@ConditionalOnExpression("!'${attachment.cloud.aliyun}'.isEmpty()")
@ConfigurationProperties(prefix = "attachment.cloud.aliyun")
public class AttachmentAliyunCloudProperties {
    String endpoint;
    String accessKeyId;
    String accessKeySecret;
    String bucket;
}
