package com.github.rich.base.feign.fallback;

import com.github.rich.base.dto.User;
import com.github.rich.base.dto.UserDetailDTO;
import com.github.rich.base.feign.RemoteUserService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author Petty
 */
@Slf4j
@Component
public class RemoteUserServiceFallbackImpl implements RemoteUserService {

    @Setter
    private Throwable cause;

    @Override
    public User findUserByLoginName(String loginName) {
        log.error("Feign---RemoteUserService->findUserByLoginName Hystrix Fusing->Params:{},Date:{},Cause:{}", loginName, System.currentTimeMillis(), cause);
        return null;
    }

    @Override
    public User findUserByMobile(String mobile) {
        log.error("Feign---RemoteUserService->findUserByMobile Hystrix Fusing->Params:{},Date:{},Cause:{}", mobile, System.currentTimeMillis(), cause);
        return null;
    }

    @Override
    public User findByWeChatOpenId(String openid) {
        log.error("Feign---RemoteUserService->findByWeChatOpenID Hystrix Fusing->Params:{},Date:{},Cause:{}", openid, System.currentTimeMillis(), cause);
        return null;
    }

    @Override
    public User findByWeChatUnionId(String unionid) {
        log.error("Feign---RemoteUserService->findByWeChatUnionID Hystrix Fusing->Params:{},Date:{},Cause:{}", unionid, System.currentTimeMillis(), cause);
        return null;
    }

    @Override
    public UserDetailDTO getUserDetail(String userId) {
        log.error("Feign---RemoteUserService->getUserDetail Hystrix Fusing->Params:{},Date:{},Cause:{}", userId, System.currentTimeMillis(), cause);
        return null;
    }
}
