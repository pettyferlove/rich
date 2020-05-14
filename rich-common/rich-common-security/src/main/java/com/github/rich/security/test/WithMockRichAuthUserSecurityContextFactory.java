package com.github.rich.security.test;

import com.github.rich.base.domain.dto.User;
import com.github.rich.security.userdetails.RichUserDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;
import org.springframework.util.StringUtils;

import java.util.*;

final class WithMockRichAuthUserSecurityContextFactory implements
        WithSecurityContextFactory<WithMockRichAuthUser> {

    @Override
    public SecurityContext createSecurityContext(WithMockRichAuthUser withUser) {
        String loginName = StringUtils.hasLength(withUser.loginName()) ? withUser
                .loginName() : withUser.value();
        if (loginName == null) {
            throw new IllegalArgumentException(withUser
                    + " cannot have null username on both loginName and value properites");
        }

        List<String> roles = new LinkedList<>();
        List<String> permissions = new LinkedList<>();

        Collections.addAll(roles, withUser.roles());

        Collections.addAll(permissions, withUser.permissions());

        User principal = User.builder()
                .id(withUser.id())
                .loginName(loginName)
                .userName(withUser.username())
                .password(withUser.password())
                .status(withUser.status())
                .roles(roles)
                .permissions(permissions)
                .build();
        RichUserDetails userDetails = new RichUserDetails(principal);
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails, userDetails.getPassword(), userDetails.getAuthorities());
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        return context;
    }
}

