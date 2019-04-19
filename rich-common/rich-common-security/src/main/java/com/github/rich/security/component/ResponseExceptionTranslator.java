package com.github.rich.security.component;

import com.github.rich.security.exception.RichOAuth2Exception;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InsufficientScopeException;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.common.exceptions.UnsupportedResponseTypeException;
import org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpRequestMethodNotSupportedException;

/**
 * spring security 异常转化器
 * 默认的国际化的异常信息在 spring-security-code下messages_zh_CN
 * LdapAuthenticationProvider.badCredentials=坏的凭证
 * LdapAuthenticationProvider.credentialsExpired=用户凭证已过期
 * LdapAuthenticationProvider.disabled=用户已失效
 * LdapAuthenticationProvider.expired=用户帐号已过期
 * LdapAuthenticationProvider.locked=用户帐号已被锁定
 * LdapAuthenticationProvider.emptyUsername=用户名不允许为空
 * LdapAuthenticationProvider.onlySupports=仅仅支持UsernamePasswordAuthenticationToken
 * PasswordComparisonAuthenticator.badCredentials=坏的凭证
 *
 * @author Petty
 */
public class ResponseExceptionTranslator extends DefaultWebResponseExceptionTranslator {

    private static final String BAD_MSG = "Bad credentials";

    private static final String INVALID_REFRESH_TOKEN = "Invalid refresh token";

    private static final String INVALID_TOKEN = "invalid_token";

    private static final String USER_DISABLED = "User is disabled";

    /**
     * @param e spring security内部异常
     * @return 经过处理的异常信息
     * @throws Exception 通用异常
     */
    @Override
    public ResponseEntity<OAuth2Exception> translate(Exception e) {
        OAuth2Exception oAuth2Exception;
        if (e instanceof InvalidGrantException && StringUtils.equals(BAD_MSG, e.getMessage())) {
            oAuth2Exception = new InvalidGrantException("密码错误", e);
        } else if (e instanceof InvalidGrantException && StringUtils.contains(e.getMessage(), INVALID_REFRESH_TOKEN)) {
            oAuth2Exception = new InvalidGrantException("refresh_token失效", e);
        } else if (e instanceof InternalAuthenticationServiceException) {
            oAuth2Exception = new InvalidGrantException("用户名不存在", e);
        } else if (e instanceof InvalidGrantException && StringUtils.contains(e.getMessage(), USER_DISABLED)) {
            oAuth2Exception = new InvalidGrantException("用户无效或被锁定", e);
        } else if (e instanceof HttpRequestMethodNotSupportedException) {
            oAuth2Exception = new InvalidGrantException("不支持的请求方式", e);
        } else {
            oAuth2Exception = new UnsupportedResponseTypeException(e.getMessage(), e);
        }
        return handleOAuth2Exception(oAuth2Exception);
    }

    /**
     * 包装异常
     *
     * @param e Exception
     * @return ResponseEntity
     */
    private ResponseEntity<OAuth2Exception> handleOAuth2Exception(OAuth2Exception e) {
        int status = e.getHttpErrorCode();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Cache-Control", "no-store");
        headers.set("Pragma", "no-cache");
        if (status == HttpStatus.UNAUTHORIZED.value() || (e instanceof InsufficientScopeException)) {
            headers.set("WWW-Authenticate", String.format("%s %s", OAuth2AccessToken.BEARER_TYPE, e.getSummary()));
        }
        RichOAuth2Exception richOAuth2Exception = new RichOAuth2Exception(e.getMessage(), e);
        return new ResponseEntity<>(richOAuth2Exception, headers,
                HttpStatus.valueOf(status));

    }
}
