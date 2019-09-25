package com.github.rich.auth.granter;

import cn.hutool.core.util.StrUtil;
import com.github.rich.common.core.constants.SecurityConstant;
import com.github.rich.security.service.RichUserDetailsService;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AbstractTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 微信登录
 *
 * @author Petty
 */
public class WeChatTokenGranter extends AbstractTokenGranter {

    private final String OPEN_ID_KEY = "open_id";
    private final String UNION_ID_KEY = "union_id";

    private static final String GRANT_TYPE = SecurityConstant.WECHAT;

    private final RichUserDetailsService userDetailsService;

    public WeChatTokenGranter(AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory, RichUserDetailsService userDetailsService) {
        super(tokenServices, clientDetailsService, requestFactory, GRANT_TYPE);
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest) {
        Map<String, String> parameters = new LinkedHashMap<String, String>(tokenRequest.getRequestParameters());
        UserDetails userDetails = null;
        if (parameters.containsKey(UNION_ID_KEY)) {
            String unionId = parameters.get(UNION_ID_KEY);
            if (StrUtil.isNotBlank(unionId)) {
                userDetails = userDetailsService.loadUserByWeChatUnionId(unionId);
            }
        } else if (parameters.containsKey(OPEN_ID_KEY)) {
            String openId = parameters.get(OPEN_ID_KEY);
            if (StrUtil.isNotBlank(openId)) {
                userDetails = userDetailsService.loadUserByWeChatOpenId(openId);
            }
        }
        if (userDetails == null) {
            throw new OAuth2Exception("请求参数异常");
        }
        AbstractAuthenticationToken userAuth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        userAuth.setDetails(parameters);
        OAuth2Request authRequest = getRequestFactory().createOAuth2Request(client, tokenRequest);
        return new OAuth2Authentication(authRequest, userAuth);
    }
}
