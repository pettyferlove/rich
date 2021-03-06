package com.github.rich.security.utils;


import cn.hutool.core.util.StrUtil;
import com.github.rich.common.core.constants.SecurityConstant;
import com.github.rich.security.userdetails.RichUserDetails;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 安全工具类
 *
 * @author Petty
 */
@UtilityClass
public class SecurityUtil {

	/**
	 * 获取Authentication
	 */
	public Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

	/**
	 * 获取用户
	 */
	private RichUserDetails getUser(Authentication authentication) {
		Object principal = authentication.getPrincipal();
		if (principal instanceof RichUserDetails) {
			return (RichUserDetails) principal;
		}
		return null;
	}

	/**
	 * 获取Check_Token对象
	 * @return OAuth2Request
	 */
	public OAuth2Request getOAuth2Request() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication instanceof OAuth2Authentication) {
			OAuth2Authentication auth2Authentication = (OAuth2Authentication) authentication;
			return auth2Authentication.getOAuth2Request();
		}
		return null;
	}

	/**
	 * 获取用户
	 */
	public RichUserDetails getUser() {
		Authentication authentication = getAuthentication();
		if (authentication == null) {
			return null;
		}
		return getUser(authentication);
	}

	/**
	 * 获取用户角色信息
	 *
	 * @return 角色集合
	 */
	public List<String> getRoles() {
		Authentication authentication = getAuthentication();
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

		List<String> roles = new ArrayList<>();
		authorities.stream()
			.filter(granted -> StrUtil.startWith(granted.getAuthority(), SecurityConstant.ROLE_PREFIX))
			.forEach(granted -> {
				String id = StrUtil.removePrefix(granted.getAuthority(), SecurityConstant.ROLE_PREFIX);
				roles.add(id);
			});
		return roles;
	}
}
