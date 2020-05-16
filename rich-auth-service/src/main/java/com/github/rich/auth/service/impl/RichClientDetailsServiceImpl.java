package com.github.rich.auth.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.rich.base.domain.dto.OAuthClientDetails;
import com.github.rich.base.feign.ClientServiceFeignClient;
import com.github.rich.security.constants.EncryptionConstant;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.security.oauth2.common.exceptions.InvalidClientException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.util.Map;

/**
 * @author Petty
 */
@Primary
@Service
@Slf4j
public class RichClientDetailsServiceImpl extends JdbcClientDetailsService {

    private final ClientServiceFeignClient clientServiceFeignClient;

    public RichClientDetailsServiceImpl(DataSource dataSource, ClientServiceFeignClient clientServiceFeignClient) {
        super(dataSource);
        this.clientServiceFeignClient = clientServiceFeignClient;
    }

    /**
     * 重写接口但是不额外增加逻辑，增加缓存即可
     * @param clientId 客户端ID
     * @return ClientDetails
     * @throws InvalidClientException 客户端无效异常
     */
    @Override
    @SneakyThrows
    public ClientDetails loadClientByClientId(String clientId) throws InvalidClientException {
        OAuthClientDetails clientDetails = clientServiceFeignClient.loadClientByClientId(clientId);
        BaseClientDetails baseClientDetails = new BaseClientDetails(clientDetails.getClientId(),clientDetails.getResourceIds(),
                clientDetails.getScope(),clientDetails.getAuthorizedGrantTypes(), clientDetails.getAuthorities(),clientDetails.getWebServerRedirectUri());
        baseClientDetails.setClientSecret(EncryptionConstant.SIGNATURE_NOOP + clientDetails.getClientSecret());
        if (clientDetails.getAccessTokenValidity() != null) {
            baseClientDetails.setAccessTokenValiditySeconds(clientDetails.getAccessTokenValidity());
        }
        if (clientDetails.getRefreshTokenValidity()!= null) {
            baseClientDetails.setRefreshTokenValiditySeconds(clientDetails.getRefreshTokenValidity());
        }


        String json = clientDetails.getAdditionalInformation();
        if (json != null) {
            try {
                @SuppressWarnings("unchecked")
                Map<String, Object> additionalInformation = JSON.parseObject(json).getInnerMap();
                baseClientDetails.setAdditionalInformation(additionalInformation);
            }
            catch (Exception e) {
                log.warn("Could not decode JSON for additional information: " + baseClientDetails, e);
            }
        }
        String scopes = clientDetails.getAutoapprove();
        if (scopes != null) {
            baseClientDetails.setAutoApproveScopes(StringUtils.commaDelimitedListToSet(scopes));
        }

        return baseClientDetails;
    }
}
