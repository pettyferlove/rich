package com.github.rich.security.feign;

import com.github.rich.common.core.context.BaseContextHandler;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.security.oauth2.client.AccessTokenContextRelay;
import org.springframework.cloud.security.oauth2.client.feign.OAuth2FeignRequestInterceptor;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;


/**
 * @author Petty
 */
@Slf4j
public class RichFeignClientInterceptor extends OAuth2FeignRequestInterceptor {
    private final OAuth2ClientContext oAuth2ClientContext;
    private final AccessTokenContextRelay accessTokenContextRelay;

    /**
     * Default constructor which uses the provided OAuth2ClientContext and Bearer tokens
     * within Authorization header
     *
     * @param oAuth2ClientContext     provided context
     * @param resource                type of resource to be accessed
     * @param accessTokenContextRelay token中继
     * @see org.springframework.cloud.security.oauth2.client.ResourceServerTokenRelayAutoConfiguration 强制使用Token中转，不管是什么请求
     */
    RichFeignClientInterceptor(OAuth2ClientContext oAuth2ClientContext
            , OAuth2ProtectedResourceDetails resource, AccessTokenContextRelay accessTokenContextRelay) {
        super(oAuth2ClientContext, resource);
        this.oAuth2ClientContext = oAuth2ClientContext;
        this.accessTokenContextRelay = accessTokenContextRelay;
    }


    /**
     * 这里可以根据条件判断是否需要附加Token到请求链中，防止性能消耗
     *
     * @param template RequestTemplate
     */
    @Override
    public void apply(RequestTemplate template) {
        boolean needSecurity = false;
        try {
            needSecurity = (boolean) BaseContextHandler.get("x-inner-service-security");
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.debug("已忽略内部服务调用权限校验");
            }
        } finally {
            BaseContextHandler.remove();
        }
        if (!needSecurity) {
            return;
        }
        accessTokenContextRelay.copyToken();
        if (oAuth2ClientContext != null
                && oAuth2ClientContext.getAccessToken() != null) {
            super.apply(template);
        }
    }
}
