package com.github.rich.auth.utils;

import com.github.rich.base.dto.User;
import com.github.rich.common.core.constant.CommonConstant;
import com.github.rich.common.core.constant.SecurityConstant;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Petty
 * UserDetails接口实现
 */
@Data
public class UserDetailsImpl implements UserDetails {
    private static final long serialVersionUID = 1L;

    private String username;
    private String password;
    private String status;
    private String type;
    private String name;
    private List<String> roles;

    public UserDetailsImpl(User user) {
        this.username = user.getUserCode();
        this.password = user.getPassword();
        this.status = user.getStatus();
        this.type = user.getUserType();
        this.name = user.getUserName();
        roles = user.getRoles();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authoritySet = new HashSet<>();
        for (String role : roles) {
            authoritySet.add(new SimpleGrantedAuthority(role));
        }
        //增加基础用户角色 ROLE_USER
        //如果用户自身具备 ROLE_USER则通过Set唯一性保留一个
        authoritySet.add(new SimpleGrantedAuthority(SecurityConstant.BASE_ROLE));
        return authoritySet;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return !CommonConstant.STATUS_INVALID.equals(status);
    }

    /**
     * 用户是否锁定
     *
     * @return True 表示锁定，False 表示未锁定
     */
    @Override
    public boolean isAccountNonLocked() {
        return !CommonConstant.STATUS_LOCK.equals(status);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 用户是否有效
     *
     * @return True有效，False无效
     */
    @Override
    public boolean isEnabled() {
        return CommonConstant.STATUS_NORMAL.equals(status);
    }
}
