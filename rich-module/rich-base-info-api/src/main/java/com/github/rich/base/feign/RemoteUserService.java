package com.github.rich.base.feign;

import com.github.rich.base.dto.User;
import com.github.rich.base.feign.factory.RemoteUserServiceFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author Petty
 */
@FeignClient(name = "rich-base-info-service", fallbackFactory = RemoteUserServiceFallbackFactory.class)
public interface RemoteUserService {

    /**
     * 通过用户名查询用户信息
     * @param userCode 用户名
     * @return 用户信息实体类
     */
    @PostMapping(value = "/user/find/code/{userCode}")
    User findUserByUsername(@PathVariable("userCode") String userCode);

    /**
     * 通过手机号码查询用户信息
     * @param mobile 手机号码
     * @return 用户信息实体类
     */
    @PostMapping(value = "/user/find/mobile/{mobile}")
    User findUserByMobile(@PathVariable("mobile") String mobile);
}
