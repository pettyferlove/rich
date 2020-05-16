package com.github.rich.base.feign.fallback;

import com.github.rich.base.domain.dto.User;
import com.github.rich.base.domain.dto.UserDetailDTO;
import com.github.rich.base.feign.UserServiceFeignClient;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Petty
 */
@Slf4j
public class UserServiceFeignClientFallbackImpl implements UserServiceFeignClient {

    @Setter
    private Throwable cause;

    @Override
    public User findUserByLoginName(String loginName) {
        log.error("Feign---UserServiceFeignClient->findUserByLoginName Hystrix Fusing->Params:{},Date:{},Cause:{}", loginName, System.currentTimeMillis(), cause);
        return null;
    }

    @Override
    public User findUserByMobile(String mobile) {
        log.error("Feign---UserServiceFeignClient->findUserByMobile Hystrix Fusing->Params:{},Date:{},Cause:{}", mobile, System.currentTimeMillis(), cause);
        return null;
    }

    @Override
    public User findByWeChatOpenId(String openid) {
        log.error("Feign---UserServiceFeignClient->findByWeChatOpenID Hystrix Fusing->Params:{},Date:{},Cause:{}", openid, System.currentTimeMillis(), cause);
        return null;
    }

    @Override
    public User findByWeChatUnionId(String unionid) {
        log.error("Feign---UserServiceFeignClient->findByWeChatUnionID Hystrix Fusing->Params:{},Date:{},Cause:{}", unionid, System.currentTimeMillis(), cause);
        return null;
    }

    @Override
    public UserDetailDTO getUserDetail(String userId) {
        log.error("Feign---UserServiceFeignClient->getUserDetail Hystrix Fusing->Params:{},Date:{},Cause:{}", userId, System.currentTimeMillis(), cause);
        return null;
    }
}
