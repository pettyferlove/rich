package com.github.rich.auth.feign.fallback;

import com.github.rich.auth.feign.UserService;
import com.github.rich.common.core.model.auth.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author Petty
 */
@Slf4j
@Service
public class UserServiceFallbackImpl implements UserService {
    @Override
    public User findUserByUsername(String username) {
        log.error("Eda-OAuth2-Service Feign---UserService->findUserByUsername Hystrix Fusing->Params:{},Date:{}",username,System.currentTimeMillis());
        return null;
    }

    @Override
    public User findUserByMobile(String mobile) {
        log.error("Eda-OAuth2-Service Feign---UserService->findUserByMobile Hystrix Fusing->Params:{},Date:{}",mobile,System.currentTimeMillis());
        return null;
    }
}
