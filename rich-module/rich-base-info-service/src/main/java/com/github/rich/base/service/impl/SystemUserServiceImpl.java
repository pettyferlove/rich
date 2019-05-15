package com.github.rich.base.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.rich.base.constant.CacheConstant;
import com.github.rich.base.dto.User;
import com.github.rich.base.entity.SystemRole;
import com.github.rich.base.entity.SystemUser;
import com.github.rich.base.mapper.SystemUserMapper;
import com.github.rich.base.service.ISystemUserRoleService;
import com.github.rich.base.service.ISystemUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.rich.common.core.utils.ConverterUtil;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

/**
 * <p>
 * 用户信息 服务实现类
 * </p>
 *
 * @author Petty
 * @since 2019-04-23
 */
@Service
public class SystemUserServiceImpl extends ServiceImpl<SystemUserMapper, SystemUser> implements ISystemUserService {

    private final ISystemUserRoleService systemUserRoleService;

    public SystemUserServiceImpl(ISystemUserRoleService systemUserRoleService) {
        this.systemUserRoleService = systemUserRoleService;
    }

    @Override
    @Cacheable(value = CacheConstant.API_PREFIX + "base-api-user", key = "#loginCode", condition = "#loginCode!=null")
    public User findByLoginCode(String loginCode) {
        SystemUser systemUser = this.getOne(new QueryWrapper<SystemUser>().eq("login_code", loginCode));
        Optional<User> userOptional = Optional.ofNullable(ConverterUtil.convert(systemUser, new User()));
        userOptional.ifPresent(user -> user.setRoles(this.loadRoles(user.getCode())));
        return userOptional.orElseGet(User::new);
    }

    @Override
    @Cacheable(value = CacheConstant.API_PREFIX + "base-api-user", key = "#mobile", condition = "#mobile!=null")
    public User findByMobile(String mobile) {
        SystemUser systemUser = this.getOne(new QueryWrapper<SystemUser>().eq("mobile_tel", mobile));
        Optional<User> userOptional = Optional.ofNullable(ConverterUtil.convert(systemUser, new User()));
        userOptional.ifPresent(user -> user.setRoles(this.loadRoles(user.getCode())));
        return userOptional.orElseGet(User::new);
    }

    @Override
    @Cacheable(value = CacheConstant.API_PREFIX + "base-api-user", key = "#openid", condition = "#openid!=null")
    public User findByWeChatOpenID(String openid) {
        SystemUser systemUser = this.getOne(new QueryWrapper<SystemUser>().eq("wechat_openid", openid));
        Optional<User> userOptional = Optional.ofNullable(ConverterUtil.convert(systemUser, new User()));
        userOptional.ifPresent(user -> user.setRoles(this.loadRoles(user.getCode())));
        return userOptional.orElseGet(User::new);
    }

    @Override
    @Cacheable(value = CacheConstant.API_PREFIX + "base-api-user", key = "#unionid", condition = "#unionid!=null")
    public User findByWeChatUnionID(String unionid) {
        SystemUser systemUser = this.getOne(new QueryWrapper<SystemUser>().eq("wechat_unionid", unionid));
        Optional<User> userOptional = Optional.ofNullable(ConverterUtil.convert(systemUser, new User()));
        userOptional.ifPresent(user -> user.setRoles(this.loadRoles(user.getCode())));
        return userOptional.orElseGet(User::new);
    }

    @Override
    public Boolean registerByWeChatOpenID(String openid, String unionid) {
        SystemUser systemUser = new SystemUser();
        systemUser.setId(UUID.randomUUID().toString().replace("-", ""));
        systemUser.setCode(UUID.randomUUID().toString().replace("-", ""));
        systemUser.setLoginCode("wx_" + UUID.randomUUID().toString().replace("-", ""));
        systemUser.setUserName("");
        systemUser.setPassword("");
        systemUser.setWechatOpenid(openid);
        systemUser.setWechatUnionid(unionid);
        systemUser.setUserType(1);
        systemUser.setStatus(1);
        systemUser.setCreateTime(LocalDateTime.now());
        systemUser.setModifierTime(LocalDateTime.now());
        return this.save(systemUser);
    }

    private List<String> loadRoles(String userCode) {
        List<SystemRole> systemRoles = systemUserRoleService.findRoleByUserCode(userCode);
        Set<String> roles = new HashSet<>();
        systemRoles.forEach(role -> roles.add(role.getRole()));
        return new ArrayList<>(roles);
    }
}
