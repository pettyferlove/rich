package com.github.rich.thirdparty.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

/**
 * 第三方平台配置
 *
 * @author Petty
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Configuration
@RefreshScope
@ConditionalOnExpression("!'${third-party}'.isEmpty()")
@ConfigurationProperties(prefix = "third-party")
public class ThirdPartyPlatformPropertiesConfig {

    private WeChatProperties wechat;

    private AlipayProperties alipay;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static public class WeChatProperties {
        String appId;
        String appSecret;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static public class AlipayProperties {
        String appId;
    }

}
