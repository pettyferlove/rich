package com.github.rich.base.feign.factory;

import com.github.rich.base.feign.UserServiceFeignClient;
import com.github.rich.base.feign.fallback.UserServiceFeignClientFallbackImpl;
import feign.hystrix.FallbackFactory;

/**
 * @author Petty
 */
public class UserServiceFallbackFactory implements FallbackFactory<UserServiceFeignClient> {
    @Override
    public UserServiceFeignClient create(Throwable throwable) {
        UserServiceFeignClientFallbackImpl userServiceFallback = new UserServiceFeignClientFallbackImpl();
        userServiceFallback.setCause(throwable);
        return userServiceFallback;
    }
}
