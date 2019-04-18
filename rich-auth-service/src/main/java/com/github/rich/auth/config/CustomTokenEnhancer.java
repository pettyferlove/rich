package com.github.rich.auth.config;

import com.github.rich.auth.service.impl.UserDetailsImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;
import java.util.Map;

/**
 * Jwt增强
 * @author Petty
 * @date 2018年3月4日
 */
@Slf4j
public class CustomTokenEnhancer implements TokenEnhancer {
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        final Map<String, Object> additionalInfo = new HashMap<>(256);
        try{
            UserDetailsImpl user = (UserDetailsImpl) authentication.getUserAuthentication().getPrincipal();
            additionalInfo.put("username", user.getUsername());
            additionalInfo.put("authorities", user.getAuthorities());
            additionalInfo.put("type", user.getType());
            additionalInfo.put("name", user.getName());
            ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
        }catch (NullPointerException e){
            log.warn("no user info");
        }
        return accessToken;
    }
}
