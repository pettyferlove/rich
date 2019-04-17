package com.github.rich.auth.utils;

import com.github.rich.common.core.model.base.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Objects;


/**
 * @author Petty
 */
@Slf4j
public class RoleUtils {


    // TODO 需要实现接口获取用户信息
    public static UserInfo getUser() {
        try {
            SecurityContext ctx = SecurityContextHolder.getContext();
            Authentication auth = ctx.getAuthentication();
            UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
            UserInfo userInfo = new UserInfo();
            userInfo.setFullName(userDetails.getUsername());
            userInfo.setUserName(userDetails.getUsername());
            return userInfo;
        } catch (Exception e) {
            log.error("获取用户信息异常", e);
        }
        // 如果没有登录，则返回实例化空的User对象。
        return null;
    }

    /**
     * 判断是否存在该角色
     * @param authorities
     * @param role
     * @return
     */
    public static boolean hasRole(Collection<? extends GrantedAuthority> authorities, String role) {
        if (CollectionUtils.isEmpty(authorities)) {
            return false;
        }
        GrantedAuthority authority = authorities.stream()
                .filter(auth -> auth.getAuthority().equals(role)).findAny()
                .orElse(null);
        return Objects.nonNull(authority);
    }
}
