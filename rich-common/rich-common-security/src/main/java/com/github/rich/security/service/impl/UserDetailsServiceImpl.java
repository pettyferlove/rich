package com.github.rich.security.service.impl;

import com.github.rich.base.dto.User;
import com.github.rich.base.feign.UserServiceFeignClient;
import com.github.rich.security.service.RichUserDetailsService;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 通过远程调用的方式实现UserDetailsService
 * Spring Security默认会使用该Bean进行用户信息获取
 *
 * @author Petty
 * @version 1.0.0
 * @see org.springframework.security.core.userdetails.UserDetailsService
 */
@Service("userDetailsService")
public class UserDetailsServiceImpl implements RichUserDetailsService {

    private final UserServiceFeignClient userServiceFeignClient;

    @Autowired
    public UserDetailsServiceImpl(UserServiceFeignClient userServiceFeignClient) {
        this.userServiceFeignClient = userServiceFeignClient;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userServiceFeignClient.findUserByLoginName(username);
        Preconditions.checkNotNull(user.getId(), "没有找到该用户");
        return new UserDetailsImpl(user);
    }

    @Override
    public UserDetails loadUserByMobile(String mobile) throws UsernameNotFoundException {
        User user = userServiceFeignClient.findUserByMobile(mobile);
        Preconditions.checkNotNull(user.getId(), "手机号码没有注册或者未与账号绑定");
        return new UserDetailsImpl(user);
    }

    @Override
    public UserDetails loadUserByWeChatOpenId(String openid) {
        User user = userServiceFeignClient.findByWeChatOpenId(openid);
        Preconditions.checkNotNull(user.getId(), "微信号未绑定");
        return new UserDetailsImpl(user);
    }

    @Override
    public UserDetails loadUserByWeChatUnionId(String unionid) {
        User user = userServiceFeignClient.findByWeChatUnionId(unionid);
        Preconditions.checkNotNull(user.getId(), "微信号未绑定");
        return new UserDetailsImpl(user);
    }
}
