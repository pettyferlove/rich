package com.github.rich.base.feign.factory;

import com.github.rich.base.feign.RemoteUserService;
import com.github.rich.base.feign.fallback.RemoteUserServiceFallbackImpl;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @author Petty
 */
@Component
public class RemoteUserServiceFallbackFactory implements FallbackFactory<RemoteUserService> {
    @Override
    public RemoteUserService create(Throwable throwable) {
        RemoteUserServiceFallbackImpl userServiceFallback = new RemoteUserServiceFallbackImpl();
        userServiceFallback.setCause(throwable);
        return userServiceFallback;
    }
}
