package com.github.rich.security.service.impl;

import com.github.rich.security.service.RichUserDetailsService;
import com.github.rich.base.dto.User;
import com.github.rich.base.feign.RemoteUserService;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author Petty
 */
@Service("userDetailsService")
public class UserDetailsServiceImpl implements RichUserDetailsService {

    private final RemoteUserService remoteUserService;

    @Autowired
    public UserDetailsServiceImpl(RemoteUserService remoteUserService) {
        this.remoteUserService = remoteUserService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = remoteUserService.findUserByUsername(username);
        Preconditions.checkNotNull(user, "no user");
        return new UserDetailsImpl(user);
    }

    @Override
    public UserDetails loadUserByMobile(String mobile) throws UsernameNotFoundException {
        User user = remoteUserService.findUserByMobile(mobile);
        Preconditions.checkNotNull(user, "no user");
        return new UserDetailsImpl(user);
    }
}
