package com.github.rich.base.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.rich.base.constant.CacheConstant;
import com.github.rich.base.dto.User;
import com.github.rich.base.entity.SystemRole;
import com.github.rich.base.entity.SystemUser;
import com.github.rich.base.entity.SystemUserExtend;
import com.github.rich.base.mapper.SystemUserMapper;
import com.github.rich.base.service.ISystemUserExtendService;
import com.github.rich.base.service.ISystemUserRoleService;
import com.github.rich.base.service.ISystemUserService;
import com.github.rich.common.core.utils.ConverterUtil;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    private final ISystemUserExtendService systemUserExtendService;

    public SystemUserServiceImpl(ISystemUserRoleService systemUserRoleService, ISystemUserExtendService systemUserExtendService) {
        this.systemUserRoleService = systemUserRoleService;
        this.systemUserExtendService = systemUserExtendService;
    }

    @Override
    @Cacheable(value = CacheConstant.INNER_API_PREFIX + "base-api-user", key = "#loginCode", condition = "#loginCode!=null")
    public User findByLoginCode(String loginCode) {
        SystemUser systemUser = this.getOne(Wrappers.<SystemUser>lambdaQuery().eq(SystemUser::getLoginCode, loginCode));
        Optional<User> userOptional = Optional.ofNullable(ConverterUtil.convert(systemUser, new User()));
        userOptional.ifPresent(user -> user.setRoles(this.loadRoles(user.getCode())));
        return userOptional.orElseGet(User::new);
    }

    @Override
    @Cacheable(value = CacheConstant.INNER_API_PREFIX + "base-api-user", key = "#mobile", condition = "#mobile!=null")
    public User findByMobile(String mobile) {
        SystemUser systemUser = this.getOne(Wrappers.<SystemUser>lambdaQuery().eq(SystemUser::getMobileTel, mobile));
        Optional<User> userOptional = Optional.ofNullable(ConverterUtil.convert(systemUser, new User()));
        userOptional.ifPresent(user -> user.setRoles(this.loadRoles(user.getCode())));
        return userOptional.orElseGet(User::new);
    }

    @Override
    @Cacheable(value = CacheConstant.INNER_API_PREFIX + "base-api-user", key = "#openid", condition = "#openid!=null")
    public User findByWeChatOpenID(String openid) {
        Optional<SystemUserExtend> userExtendOptional = Optional.ofNullable(systemUserExtendService.getOne(Wrappers.<SystemUserExtend>lambdaQuery().eq(SystemUserExtend::getWechatOpenid,openid)));
        Optional<User> userOptional = Optional.empty();
        if(userExtendOptional.isPresent()){
            SystemUser systemUser = this.getById(userExtendOptional.get().getUserCode());
            userOptional = Optional.ofNullable(ConverterUtil.convert(systemUser, new User()));
        }
        userOptional.ifPresent(user -> user.setRoles(this.loadRoles(user.getCode())));
        return userOptional.orElseGet(User::new);
    }

    @Override
    @Cacheable(value = CacheConstant.INNER_API_PREFIX + "base-api-user", key = "#unionid", condition = "#unionid!=null")
    public User findByWeChatUnionID(String unionid) {
        Optional<SystemUserExtend> userExtendOptional = Optional.ofNullable(systemUserExtendService.getOne(Wrappers.<SystemUserExtend>lambdaQuery().eq(SystemUserExtend::getWechatUnionid,unionid)));
        Optional<User> userOptional = Optional.empty();
        if(userExtendOptional.isPresent()){
            SystemUser systemUser = this.getById(userExtendOptional.get().getUserCode());
            userOptional = Optional.ofNullable(ConverterUtil.convert(systemUser, new User()));
        }
        userOptional.ifPresent(user -> user.setRoles(this.loadRoles(user.getCode())));
        return userOptional.orElseGet(User::new);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean registerByWeChatOpenID(String openid, String unionid) {
        boolean result = false;
        String userCode = IdUtil.simpleUUID();
        SystemUser systemUser = new SystemUser();
        systemUser.setId(IdUtil.simpleUUID());
        systemUser.setCode(userCode);
        systemUser.setLoginCode("wx_" + userCode);
        systemUser.setUserName("");
        systemUser.setPassword("");
        systemUser.setUserType(0);
        systemUser.setStatus(1);
        systemUser.setCreateTime(LocalDateTime.now());
        systemUser.setModifierTime(LocalDateTime.now());
        if(this.save(systemUser)){
            SystemUserExtend systemUserExtend = new SystemUserExtend();
            systemUserExtend.setId(IdUtil.simpleUUID());
            systemUserExtend.setCode(IdUtil.simpleUUID());
            systemUserExtend.setUserCode(userCode);
            systemUserExtend.setWechatOpenid(openid);
            systemUserExtend.setWechatUnionid(unionid);
            result = systemUserExtendService.save(systemUserExtend);
        }
        return result;
    }

    private List<String> loadRoles(String userCode) {
        List<SystemRole> systemRoles = systemUserRoleService.findRoleByUserCode(userCode);
        Set<String> roles = new HashSet<>();
        systemRoles.forEach(role -> roles.add(role.getRole()));
        return new ArrayList<>(roles);
    }
}
