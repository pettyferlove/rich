package com.github.rich.auth.granter;

import com.github.rich.auth.service.CaptchaValidateService;
import com.github.rich.common.core.constant.SecurityConstant;
import com.github.rich.common.core.exception.security.CaptchaCheckException;
import com.github.rich.security.service.RichUserDetailsService;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AbstractTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Petty
 */
public class MobileTokenGranter extends AbstractTokenGranter {

    private static final String GRANT_TYPE = SecurityConstant.MOBILE;

    private final RichUserDetailsService userDetailsService;

    private final CaptchaValidateService captchaValidateService;

    public MobileTokenGranter(AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory, RichUserDetailsService userDetailsService, CaptchaValidateService captchaValidateService) {
        super(tokenServices, clientDetailsService, requestFactory, GRANT_TYPE);
        this.userDetailsService = userDetailsService;
        this.captchaValidateService = captchaValidateService;
    }

    @Override
    protected OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest) {
        Map<String, String> parameters = new LinkedHashMap<String, String>(tokenRequest.getRequestParameters());
        String mobile = parameters.get("mobile");
        String smsCode = parameters.get("sms_code");
        if (!captchaValidateService.validate(mobile, smsCode)) {
            throw new CaptchaCheckException("验证码错误");
        }
        UserDetails userDetails = userDetailsService.loadUserByMobile(mobile);
        if (userDetails == null) {
            throw new UsernameNotFoundException("手机号不存在");
        }
        AbstractAuthenticationToken userAuth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        userAuth.setDetails(parameters);
        OAuth2Request storedOAuth2Request = getRequestFactory().createOAuth2Request(client, tokenRequest);
        return new OAuth2Authentication(storedOAuth2Request, userAuth);
    }
}
