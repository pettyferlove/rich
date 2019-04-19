package com.github.rich.security.service.impl;

import com.github.rich.base.dto.User;
import com.github.rich.base.feign.RemoteUserService;
import com.github.rich.security.service.RichUserDetailsService;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 通过远程调用的方式实现UserDetailsService
 * Spring Security默认会使用该Bean进行用户信息获取
 * @author Petty
 * @version 1.0.0
 * @see org.springframework.security.core.userdetails.UserDetailsService
 */
@Service("userDetailsService")
public class UserDetailsServiceImpl implements RichUserDetailsService {

    private final RemoteUserService remoteUserService;

    @Autowired
    public UserDetailsServiceImpl(RemoteUserService remoteUserService) {
        this.remoteUserService = remoteUserService;
    }

    /**
     * 根据用户名获取用户信息（原生）
     * @param username 用户名
     * @return UserDetails
     * @throws UsernameNotFoundException 未找到用户
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = remoteUserService.findUserByUsername(username);
        Preconditions.checkNotNull(user, "no user");
        return new UserDetailsImpl(user);
    }

    /**
     * 根据用户名获取用户信息（自定义）
     * @param mobile 手机号码
     * @return UserDetails
     * @throws UsernameNotFoundException 未找到用户
     */
    @Override
    public UserDetails loadUserByMobile(String mobile) throws UsernameNotFoundException {
        User user = remoteUserService.findUserByMobile(mobile);
        Preconditions.checkNotNull(user, "no user");
        return new UserDetailsImpl(user);
    }
}
