package com.github.rich.base.feign;

import com.github.rich.base.dto.User;
import com.github.rich.base.dto.UserDetailDTO;
import com.github.rich.base.feign.factory.UserServiceFallbackFactory;
import com.github.rich.common.core.constants.CommonConstant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Petty
 */
@FeignClient(contextId = "remoteUserService", name = "rich-base-info-service", fallbackFactory = UserServiceFallbackFactory.class)
@RequestMapping(CommonConstant.INNER_SERVICE_PREFIX + "/user")
public interface UserServiceFeignClient {

    /**
     * 通过用户名查询用户信息
     *
     * @param loginName 登录名
     * @return 用户信息实体类
     */
    @GetMapping("{loginName}")
    User findUserByLoginName(@PathVariable("loginName") String loginName);

    /**
     * 通过手机号码查询用户信息
     *
     * @param mobile 手机号码
     * @return 用户信息实体类
     */
    @GetMapping("{mobile}")
    User findUserByMobile(@PathVariable("mobile") String mobile);


    /**
     * 通过微信OpenID查询用户及其角色信息
     *
     * @param openid OpenID
     * @return User
     */
    @GetMapping("wechat/open/{openid}")
    User findByWeChatOpenId(@PathVariable("openid") String openid);

    /**
     * 通过微信UnionID查询用户及其角色信息
     *
     * @param unionid UnionID
     * @return User
     */
    @GetMapping("wechat/union/{unionid}")
    User findByWeChatUnionId(@PathVariable("unionid") String unionid);

    /**
     * 通过用户ID获取用户信息详情
     *
     * @param userId 用户ID
     * @return UserDetailDTO
     */
    @GetMapping("detail/{userId}")
    UserDetailDTO getUserDetail(@PathVariable("userId") String userId);
}
