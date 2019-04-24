package com.github.rich.base.api;

import com.github.rich.base.dto.User;
import com.github.rich.common.core.constant.CommonConstant;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Petty
 */
@RequestMapping(CommonConstant.INNER_SERVICE_PREFIX + "/user")
public interface UserServiceApi {

    /**
     * 通过用户名查询用户及其角色信息
     *
     * @param userCode 用户名
     * @return User
     */
    @PostMapping("/find/code/{userCode}")
    User findByCode(@PathVariable String userCode);

    /**
     * 通过手机号查询用户及其角色信息
     *
     * @param mobile 手机号码
     * @return User
     */
    @PostMapping("/find/mobile/{mobile}")
    User findByMobile(@PathVariable String mobile);

    /**
     * 通过微信OpenID查询用户及其角色信息
     *
     * @param openid OpenID
     * @return User
     */
    @PostMapping("/find/wechat/open/{openid}")
    User findByWeChatOpenID(@PathVariable String openid);

    /**
     * 通过微信UnionID查询用户及其角色信息
     *
     * @param unionid UnionID
     * @return User
     */
    @PostMapping("/find/wechat/union/{unionid}")
    User findByWeChatUnionID(@PathVariable String unionid);
}
