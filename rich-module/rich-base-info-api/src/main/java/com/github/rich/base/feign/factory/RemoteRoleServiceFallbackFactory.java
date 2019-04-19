package com.github.rich.base.feign.factory;

import com.github.rich.base.feign.RemoteRoleService;
import com.github.rich.base.feign.fallback.RemoteRoleServiceFallbackImpl;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class RemoteRoleServiceFallbackFactory  implements FallbackFactory<RemoteRoleService> {
    @Override
    public RemoteRoleService create(Throwable throwable) {
        RemoteRoleServiceFallbackImpl remoteRoleServiceFallback = new RemoteRoleServiceFallbackImpl();
        remoteRoleServiceFallback.setCause(throwable);
        return remoteRoleServiceFallback;
    }
}