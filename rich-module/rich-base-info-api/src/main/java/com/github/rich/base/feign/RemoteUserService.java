package com.github.rich.base.feign;

import com.github.rich.base.model.User;
import com.github.rich.base.feign.factory.RemoteUserServiceFallbackFactory;
import com.github.rich.common.core.constant.CommonConstant;
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
     *
     * @param userCode 用户名
     * @return 用户信息实体类
     */
    @PostMapping(value = CommonConstant.INNER_SERVICE_PREFIX + "/user/find/code/{userCode}")
    User findUserByUsername(@PathVariable("userCode") String userCode);

    /**
     * 通过手机号码查询用户信息
     *
     * @param mobile 手机号码
     * @return 用户信息实体类
     */
    @PostMapping(value = CommonConstant.INNER_SERVICE_PREFIX + "/user/find/mobile/{mobile}")
    User findUserByMobile(@PathVariable("mobile") String mobile);


    /**
     * 通过微信OpenID查询用户及其角色信息
     *
     * @param openid OpenID
     * @return User
     */
    @PostMapping(value = CommonConstant.INNER_SERVICE_PREFIX + "/user/find/wechat/open/{openid}")
    User findByWeChatOpenID(@PathVariable("openid") String openid);

    /**
     * 通过微信UnionID查询用户及其角色信息
     *
     * @param unionid UnionID
     * @return User
     */
    @PostMapping(value = CommonConstant.INNER_SERVICE_PREFIX + "/user/find/wechat/union/{unionid}")
    User findByWeChatUnionID(@PathVariable("unionid") String unionid);
}
