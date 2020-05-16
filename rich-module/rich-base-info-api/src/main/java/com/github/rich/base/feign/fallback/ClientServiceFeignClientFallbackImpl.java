package com.github.rich.base.feign.fallback;

import com.github.rich.base.domain.dto.OAuthClientDetails;
import com.github.rich.base.feign.ClientServiceFeignClient;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Petty
 */
@Slf4j
public class ClientServiceFeignClientFallbackImpl implements ClientServiceFeignClient {

    @Setter
    private Throwable cause;


    @Override
    public OAuthClientDetails loadClientByClientId(String clientId) {
        log.error("Feign---ClientServiceFeignClient->loadRoutes Hystrix Fusing->Params:{},Date:{},Cause:{}", clientId, System.currentTimeMillis(), cause);
        return null;
    }
}
