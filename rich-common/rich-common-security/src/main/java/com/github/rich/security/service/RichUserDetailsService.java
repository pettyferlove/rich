package com.github.rich.security.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * 自定义用户登录
 * 增加移动电话登录方式
 *
 * @author Petty
 */
public interface RichUserDetailsService extends UserDetailsService {

    /**
     * 移动电话登录
     *
     * @param mobile 手机号码
     * @return UserDetails
     */
    UserDetails loadUserByMobile(String mobile);


    /**
     * 通过微信OpenID查询用户及其角色信息
     *
     * @param openid OpenID
     * @return UserDetails
     */
    UserDetails loadUserByWeChatOpenID(String openid);

    /**
     * 通过微信UnionID查询用户及其角色信息
     *
     * @param unionid UnionID
     * @return UserDetails
     */
    UserDetails loadUserByWeChatUnionID(String unionid);
}
