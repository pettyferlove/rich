package com.github.rich.auth.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * 自定义用户登录
 * 增加移动电话登录方式
 * @author Petty
 */
public interface RichUserDetailsService extends UserDetailsService {

    /**
     * 移动电话登录
     * @param mobile 手机号码
     * @return UserDetails
     */
    UserDetails loadUserByMobile(String mobile);
}
