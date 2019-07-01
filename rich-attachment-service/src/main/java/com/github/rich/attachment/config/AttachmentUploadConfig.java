package com.github.rich.attachment.config;

import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Petty
 */
@Configuration
@ConditionalOnWebApplication
public class AttachmentUploadConfig {

    private final AttachmentAliyunProperties aliyunProperties;

    public AttachmentUploadConfig(AttachmentAliyunProperties aliyunProperties) {
        this.aliyunProperties = aliyunProperties;
    }

    @Bean
    @ConditionalOnExpression("!'${attachment.cloud.aliyun}'.isEmpty()")
    public OSS aliyunOSS(){
        ClientConfiguration conf = new ClientConfiguration();
        return new OSSClient(aliyunProperties.getEndpoint(),aliyunProperties.getAccessKeyId(),aliyunProperties.getAccessKeySecret(),conf);
    }
}
