package com.github.rich.auth.endpoint;

import cn.hutool.core.util.StrUtil;
import com.github.rich.auth.exception.LogoutException;
import com.github.rich.auth.model.LogoutInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.FrameworkEndpoint;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * @author Petty
 */
@FrameworkEndpoint
public class RevokeTokenEndpoint {

    private final TokenStore tokenStore;

    @Autowired
    public RevokeTokenEndpoint(TokenStore tokenStore) {
        this.tokenStore = tokenStore;
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/oauth/token")
    @ResponseBody
    public ResponseEntity<LogoutInfo> revokeToken(String token) {
        if (StringUtils.hasText(token)) {
            try {
                OAuth2AccessToken accessToken = tokenStore.readAccessToken(token);
                Map<String, Object> information = accessToken.getAdditionalInformation();
                if (StrUtil.isBlank(accessToken.getValue())) {
                    throw new LogoutException("logout fail");
                }
                tokenStore.removeAccessToken(accessToken);
                return ResponseEntity.ok(new LogoutInfo("logout success", System.currentTimeMillis(), 200));
            } catch (Exception e) {
                return ResponseEntity.status(500).body(new LogoutInfo("logout error", System.currentTimeMillis(), 500));
            }
        } else {
            return ResponseEntity.status(204).body(new LogoutInfo("no token", System.currentTimeMillis(), 200));
        }
    }
}
