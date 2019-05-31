package com.github.rich.base.api;

import com.github.rich.base.dto.User;
import com.github.rich.common.core.constant.CommonConstant;
import org.springframework.web.bind.annotation.GetMapping;
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
     * @param loginName 用户名
     * @return User
     */
    @GetMapping("/{loginName}")
    User getByLoginName(@PathVariable String loginName);

    /**
     * 通过手机号查询用户及其角色信息
     *
     * @param mobile 手机号码
     * @return User
     */
    @GetMapping("/mobile/{mobile}")
    User getByMobile(@PathVariable String mobile);

    /**
     * 通过微信OpenID查询用户及其角色信息
     *
     * @param openid OpenID
     * @return User
     */
    @GetMapping("/wechat/open/{openid}")
    User getByWeChatOpenID(@PathVariable String openid);

    /**
     * 通过微信UnionID查询用户及其角色信息
     *
     * @param unionid UnionID
     * @return User
     */
    @GetMapping("/wechat/union/{unionid}")
    User getByWeChatUnionID(@PathVariable String unionid);
}
