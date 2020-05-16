package com.github.rich.base.feign.factory;

import com.github.rich.base.feign.ClientServiceFeignClient;
import com.github.rich.base.feign.fallback.ClientServiceFeignClientFallbackImpl;
import feign.hystrix.FallbackFactory;

/**
 * @author Petty
 */
public class ClientServiceFeignClientFallbackFactory implements FallbackFactory<ClientServiceFeignClient> {
    @Override
    public ClientServiceFeignClient create(Throwable throwable) {
        ClientServiceFeignClientFallbackImpl clientServiceFeignClientFallback = new ClientServiceFeignClientFallbackImpl();
        clientServiceFeignClientFallback.setCause(throwable);
        return clientServiceFeignClientFallback;
    }
}
