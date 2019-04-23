package com.github.rich.auth.granter;

import com.github.rich.common.core.constant.SecurityConstant;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AbstractTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

/**
 * 微信登录
 * @author Petty
 */
public class WechatTokenGranter extends AbstractTokenGranter {

    private static final String GRANT_TYPE = SecurityConstant.WECHAT;

    protected WechatTokenGranter(AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory, String grantType) {
        super(tokenServices, clientDetailsService, requestFactory, GRANT_TYPE);
    }

    @Override
    protected OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest) {
        return super.getOAuth2Authentication(client, tokenRequest);
    }
}
