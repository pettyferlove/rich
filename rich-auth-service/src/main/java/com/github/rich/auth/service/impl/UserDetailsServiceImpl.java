package com.github.rich.auth.service.impl;

import com.github.rich.auth.feign.UserService;
import com.github.rich.auth.service.CustomUserDetailsService;
import com.github.rich.auth.utils.UserDetailsImpl;
import com.github.rich.common.core.model.auth.User;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author Petty
 */
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Service("userDetailsService")
public class UserDetailsServiceImpl implements CustomUserDetailsService {

    private final UserService userService;

    @Autowired
    public UserDetailsServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findUserByUsername(username);
        Preconditions.checkNotNull(user, "No user");
        return new UserDetailsImpl(user);
    }

    @Override
    public UserDetails loadUserByMobile(String mobile) throws UsernameNotFoundException {
        User user = userService.findUserByMobile(mobile);
        Preconditions.checkNotNull(user, "No user");
        return new UserDetailsImpl(user);
    }
}
