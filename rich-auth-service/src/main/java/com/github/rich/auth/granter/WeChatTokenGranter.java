package com.github.rich.auth.granter;

import com.github.rich.auth.model.WeChatAuthCallback;
import com.github.rich.auth.service.WeChatAuthService;
import com.github.rich.common.core.constant.SecurityConstant;
import com.github.rich.security.service.RichUserDetailsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AbstractTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 微信登录
 * @author Petty
 */
public class WeChatTokenGranter extends AbstractTokenGranter {

    private static final String GRANT_TYPE = SecurityConstant.WECHAT;

    private final RichUserDetailsService userDetailsService;

    private final WeChatAuthService weChatAuthService;

    public WeChatTokenGranter(AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory, RichUserDetailsService userDetailsService, WeChatAuthService weChatAuthService) {
        super(tokenServices, clientDetailsService, requestFactory, GRANT_TYPE);
        this.userDetailsService = userDetailsService;
        this.weChatAuthService = weChatAuthService;
    }

    @Override
    protected OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest) {
        Map<String, String> parameters = new LinkedHashMap<String, String>(tokenRequest.getRequestParameters());
        String code = parameters.get("code");
        if(StringUtils.isEmpty(code)){
            throw new OAuth2Exception("code不能为空");
        }
        UserDetails userDetails;
        WeChatAuthCallback auth = weChatAuthService.auth(code);
        // UnionID为空则优先查询OpenID
        if(StringUtils.isEmpty(auth.getUnionid())){
            userDetails = userDetailsService.loadUserByWeChatOpenID(auth.getOpenid());
        }else{
            try{
                userDetails = userDetailsService.loadUserByWeChatUnionID(auth.getUnionid());
            }catch (Exception e){
                userDetails = userDetailsService.loadUserByWeChatOpenID(auth.getOpenid());
            }
        }
        AbstractAuthenticationToken userAuth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        userAuth.setDetails(parameters);
        OAuth2Request storedOAuth2Request = getRequestFactory().createOAuth2Request(client, tokenRequest);
        return new OAuth2Authentication(storedOAuth2Request, userAuth);
    }
}
