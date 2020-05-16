package com.github.rich.base.feign.impl;

import com.github.rich.base.domain.dto.OAuthClientDetails;
import com.github.rich.base.feign.ClientServiceFeignClient;
import com.github.rich.base.service.ISystemOauthClientDetailsService;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Petty
 */
@RestController
public class ClientServiceFeignClientImpl implements ClientServiceFeignClient {

    private final ISystemOauthClientDetailsService systemOauthClientDetailsService;

    public ClientServiceFeignClientImpl(ISystemOauthClientDetailsService systemOauthClientDetailsService) {
        this.systemOauthClientDetailsService = systemOauthClientDetailsService;
    }

    @Override
    public OAuthClientDetails loadClientByClientId(String clientId) {
        return systemOauthClientDetailsService.get(clientId);
    }
}
