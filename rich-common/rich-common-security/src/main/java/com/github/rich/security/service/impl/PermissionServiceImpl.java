package com.github.rich.security.service.impl;

import cn.hutool.core.util.StrUtil;
import com.github.rich.common.core.constant.SecurityConstant;
import com.github.rich.security.service.PermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.PatternMatchUtils;
import org.springframework.util.StringUtils;

import java.util.Collection;

/**
 * @author Petty
 */
@Slf4j
@Component("permission")
public class PermissionServiceImpl implements PermissionService {
    @Override
    public boolean hasPermission(String permission) {
        if (StrUtil.isBlank(permission)) {
            return false;
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return false;
        }
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        return authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .filter(StringUtils::hasText)
                .filter(authority-> !authority.startsWith(SecurityConstant.ROLE_PREFIX))
                .anyMatch(x -> PatternMatchUtils.simpleMatch(permission, x));
    }
}
