package com.github.rich.security.userdetails;

import cn.hutool.core.util.StrUtil;
import com.github.rich.base.domain.dto.User;
import com.github.rich.common.core.constants.CommonConstant;
import com.github.rich.common.core.constants.SecurityConstant;
import com.github.rich.security.constants.EncryptionConstant;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

/**
 * @author Petty
 * UserDetails接口实现
 */
@Data
public class RichUserDetails implements UserDetails {
    private static final long serialVersionUID = 1L;
    private String userId;
    /**
     * 登录名
     */
    private String username;
    private String password;
    private Integer status;
    private Integer type;
    private String name;
    private Integer userType;
    private String tenantId;
    private Integer region;
    private List<String> roles;
    private List<String> permissions;

    public RichUserDetails(User user) {
        this.userId = user.getId();
        this.username = user.getLoginName();
        this.password = user.getPassword();
        this.status = user.getStatus();
        this.name = user.getUserName();
        this.userType = user.getUserType();
        this.tenantId = user.getTenantId();
        this.region = user.getRegion();
        this.roles = user.getRoles();
        this.permissions = user.getPermissions();
    }

    /**
     * 将角色和资源授权加入到 OAuth2授权集合
     *
     * @return 授权集合
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authoritySet = new HashSet<>();
        for (String permission : permissions) {
            if (StrUtil.isNotEmpty(permission)) {
                authoritySet.add(new SimpleGrantedAuthority(SecurityConstant.PERMISSION_PREFIX + permission));
            }
        }
        for (String role : roles) {
            if (StrUtil.isNotEmpty(role)) {
                authoritySet.add(new SimpleGrantedAuthority(SecurityConstant.ROLE_PREFIX + role));
            }
        }
        //增加基础用户角色 ROLE_USER
        //如果用户自身具备 ROLE_USER则通过Set唯一性保留一个
        authoritySet.add(new SimpleGrantedAuthority(SecurityConstant.ROLE_PREFIX + SecurityConstant.BASE_ROLE));
        return authoritySet;
    }

    @Override
    public String getPassword() {
        return EncryptionConstant.SIGNATURE + this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return !Objects.equals(CommonConstant.STATUS_INVALID, status);
    }

    /**
     * 用户是否锁定
     *
     * @return True 表示锁定，False 表示未锁定
     */
    @Override
    public boolean isAccountNonLocked() {
        return !Objects.equals(CommonConstant.STATUS_LOCK, status);
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
        return Objects.equals(CommonConstant.STATUS_NORMAL, status);
    }
}
