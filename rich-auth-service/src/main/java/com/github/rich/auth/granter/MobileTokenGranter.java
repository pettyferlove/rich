package com.github.rich.auth.granter;

import com.github.rich.security.service.CaptchaValidateService;
import com.github.rich.common.core.constants.SecurityConstant;
import com.github.rich.common.core.exception.security.CaptchaCheckException;
import com.github.rich.security.service.RichUserDetailsService;
import org.apache.commons.lang3.StringUtils;
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
        if(StringUtils.isEmpty(mobile)){
            throw new OAuth2Exception("手机号码不能为空");
        }
        UserDetails userDetails = userDetailsService.loadUserByMobile(mobile);
        AbstractAuthenticationToken userAuth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        userAuth.setDetails(parameters);
        OAuth2Request authRequest = getRequestFactory().createOAuth2Request(client, tokenRequest);
        return new OAuth2Authentication(authRequest, userAuth);
    }
}
