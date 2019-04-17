package com.github.rich.auth.feign;

import com.github.rich.auth.feign.fallback.UserServiceFallbackImpl;
import com.github.rich.common.core.model.auth.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author Petty
 */
@FeignClient(name = "eda-base-info-service", fallback = UserServiceFallbackImpl.class)
public interface UserService {

    /**
     * 通过用户名查询用户信息
     * @param username 用户名
     * @return 用户信息实体类
     */
    @PostMapping(value = "/user/findUserByUsername/{username}")
    User findUserByUsername(@PathVariable("username") String username);

    /**
     * 通过手机号码查询用户信息
     * @param mobile 手机号码
     * @return 用户信息实体类
     */
    @PostMapping(value = "/user/findUserByMobile/{mobile}")
    User findUserByMobile(@PathVariable("mobile") String mobile);
}
