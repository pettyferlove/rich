package com.github.rich.base.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.rich.base.constants.CacheConstant;
import com.github.rich.base.domain.dto.User;
import com.github.rich.base.entity.*;
import com.github.rich.base.mapper.SystemUserMapper;
import com.github.rich.base.service.*;
import com.github.rich.base.domain.vo.ChangeMobileVO;
import com.github.rich.base.domain.vo.ChangePasswordVO;
import com.github.rich.base.domain.vo.UserDetailVO;
import com.github.rich.base.domain.vo.UserInfoVO;
import com.github.rich.common.core.constants.CommonConstant;
import com.github.rich.common.core.exception.BaseRuntimeException;
import com.github.rich.common.core.utils.ConverterUtil;
import com.github.rich.security.constants.EncryptionConstant;
import com.github.rich.security.service.CaptchaValidateService;
import com.github.rich.security.userdetails.RichUserDetails;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    private final ISystemRoleService systemRoleService;

    private final ISystemMenuResourceService systemMenuResourceService;

    private final ISystemUserRoleService systemUserRoleService;

    private final ISystemUserExtendService systemUserExtendService;

    private final ISystemTenantService systemTenantService;

    private final PasswordEncoder passwordEncoder;

    private final PasswordEncoder userPasswordEncoder;

    private final CaptchaValidateService sensitiveInfoCaptchaValidateService;

    public SystemUserServiceImpl(ISystemRoleService systemRoleService, ISystemMenuResourceService systemMenuResourceService, ISystemUserRoleService systemUserRoleService, ISystemUserExtendService systemUserExtendService, ISystemTenantService systemTenantService, PasswordEncoder passwordEncoder, PasswordEncoder userPasswordEncoder, CaptchaValidateService sensitiveInfoCaptchaValidateService) {
        this.systemRoleService = systemRoleService;
        this.systemMenuResourceService = systemMenuResourceService;
        this.systemUserRoleService = systemUserRoleService;
        this.systemUserExtendService = systemUserExtendService;
        this.systemTenantService = systemTenantService;
        this.passwordEncoder = passwordEncoder;
        this.userPasswordEncoder = userPasswordEncoder;
        this.sensitiveInfoCaptchaValidateService = sensitiveInfoCaptchaValidateService;
    }

    /**
     * 普通登录封装
     *
     * @param type   登录类型 eg: loginName、mobile、email等
     * @param params 参数
     * @return User
     */
    private User findUser(String type, String params) {
        LambdaQueryWrapper<SystemUser> queryWrapper;
        switch (type) {
            case "userName":
                queryWrapper = Wrappers.<SystemUser>lambdaQuery().eq(SystemUser::getLoginName, params);
                break;
            case "mobile":
                queryWrapper = Wrappers.<SystemUser>lambdaQuery().eq(SystemUser::getMobileTel, params);
                break;
            default:
                throw new BaseRuntimeException("find type is error!");
        }
        SystemUser systemUser = this.getOne(queryWrapper);
        Optional<User> userOptional = Optional.ofNullable(ConverterUtil.convert(systemUser, new User()));
        userOptional.ifPresent(user -> {
            user.setRoles(this.loadRoles(user.getId()));
            user.setPermissions(this.loadPermissions(user.getId()));
            if (!verifyTenantStatus(user.getTenantId())) {
                user.setStatus(CommonConstant.STATUS_LOCK);
            }
        });
        return userOptional.orElseGet(User::new);
    }

    /**
     * 微信登录封装
     *
     * @param type   登录类型 eg: openId、unionId
     * @param params 参数
     * @return User
     */
    private User findUserByWeChat(String type, String params) {
        LambdaQueryWrapper<SystemUserExtend> queryWrapper;
        switch (type) {
            case "openId":
                queryWrapper = Wrappers.<SystemUserExtend>lambdaQuery().eq(SystemUserExtend::getWechatOpenid, params);
                break;
            case "unionId":
                queryWrapper = Wrappers.<SystemUserExtend>lambdaQuery().eq(SystemUserExtend::getWechatUnionid, params);
                break;
            default:
                throw new BaseRuntimeException("find type is error!");
        }
        Optional<SystemUserExtend> userExtendOptional = Optional.ofNullable(systemUserExtendService.getOne(queryWrapper));
        Optional<User> userOptional = Optional.empty();
        if (userExtendOptional.isPresent()) {
            SystemUser systemUser = this.getById(userExtendOptional.get().getUserId());
            userOptional = Optional.ofNullable(ConverterUtil.convert(systemUser, new User()));
        }
        userOptional.ifPresent(user -> {
            user.setRoles(this.loadRoles(user.getId()));
            user.setPermissions(this.loadPermissions(user.getId()));
            if (!verifyTenantStatus(user.getTenantId())) {
                user.setStatus(CommonConstant.STATUS_LOCK);
            }
        });
        return userOptional.orElseGet(User::new);
    }

    /**
     * 判断租户是否有效，进而判断用户是否生效 未绑定租户的则使用用户自身的状态码
     *
     * @param tenantId 租户ID
     * @return Boolean
     */
    private Boolean verifyTenantStatus(String tenantId) {
        if (StrUtil.isEmpty(tenantId)) {
            return true;
        }
        return systemTenantService.checkTenantStatus(tenantId);
    }

    @Override
    @Cacheable(value = CacheConstant.INNER_API_PREFIX + "base-api-user", key = "#loginName", condition = "#loginName!=null")
    public User findByLoginName(String loginName) {
        return this.findUser("userName", loginName);
    }

    @Override
    @Cacheable(value = CacheConstant.INNER_API_PREFIX + "base-api-user", key = "#mobile", condition = "#mobile!=null")
    public User findByMobile(String mobile) {
        return this.findUser("mobile", mobile);
    }

    @Override
    @Cacheable(value = CacheConstant.INNER_API_PREFIX + "base-api-user", key = "#openid", condition = "#openid!=null")
    public User findByWeChatOpenId(String openid) {
        return this.findUserByWeChat("openId", openid);
    }

    @Override
    @Cacheable(value = CacheConstant.INNER_API_PREFIX + "base-api-user", key = "#unionid", condition = "#unionid!=null")
    public User findByWeChatUnionId(String unionid) {
        return this.findUserByWeChat("unionId", unionid);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean registerByWeChat(String openid, String unionid) {
        boolean result = false;
        try {
            SystemUser systemUser = new SystemUser();
            // 生成随机登录名
            systemUser.setLoginName("wx_" + IdWorker.getId());
            systemUser.setUserName("");
            systemUser.setPassword("");
            systemUser.setUserType(0);
            systemUser.setStatus(1);
            systemUser.setCreateTime(LocalDateTime.now());
            systemUser.setModifyTime(LocalDateTime.now());
            if (this.save(systemUser)) {
                SystemUserExtend systemUserExtend = new SystemUserExtend();
                systemUserExtend.setUserId(systemUser.getId());
                systemUserExtend.setWechatOpenid(openid);
                systemUserExtend.setWechatUnionid(unionid);
                result = systemUserExtendService.save(systemUserExtend);
            }
        } catch (DuplicateKeyException e) {
            throw new BaseRuntimeException("用户已绑定微信公众号，不可重复绑定");
        } catch (Exception e) {
            throw new BaseRuntimeException("注册失败");
        }
        return result;
    }

    @Override
    public IPage<SystemUser> page(SystemUser user, Page<SystemUser> page) {
        return this.page(page, Wrappers.lambdaQuery(user).orderByDesc(SystemUser::getCreateTime));
    }

    @Override
    @Cacheable(value = CacheConstant.OUTER_API_PREFIX + "base-user-detail", key = "#id", condition = "#id!=null")
    public SystemUser get(String id) {
        return this.getById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @Caching(evict = {
            @CacheEvict(value = CacheConstant.INNER_API_PREFIX + "base-api-user", allEntries = true),
            @CacheEvict(value = CacheConstant.OUTER_API_PREFIX + "base-user-info-detail", allEntries = true),
            @CacheEvict(value = CacheConstant.OUTER_API_PREFIX + "base-user-detail", key = "#id", condition = "#id!=null")
    })
    public Boolean delete(String id) {
        try {
            this.removeById(id);
            systemUserRoleService.remove(Wrappers.<SystemUserRole>lambdaQuery().eq(SystemUserRole::getUserId, id));
            return true;
        } catch (Exception e) {
            throw new BaseRuntimeException("删除失败");
        }
    }

    @Override
    public String create(String userId, SystemUser user) {
        user.setPassword(userPasswordEncoder.encode(user.getPassword()));
        user.setCreator(userId);
        user.setCreateTime(LocalDateTime.now());
        if (this.save(user)) {
            return user.getId();
        } else {
            throw new BaseRuntimeException("新增失败");
        }
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = CacheConstant.INNER_API_PREFIX + "base-api-user", allEntries = true),
            @CacheEvict(value = CacheConstant.OUTER_API_PREFIX + "base-user-info-detail", allEntries = true),
            @CacheEvict(value = CacheConstant.OUTER_API_PREFIX + "base-user-detail", key = "#user.id", condition = "#user.id!=null")
    })
    public Boolean update(String userId, SystemUser user) {
        user.setModifier(userId);
        user.setModifyTime(LocalDateTime.now());
        return this.updateById(user);
    }

    @Override
    @Cacheable(value = CacheConstant.OUTER_API_PREFIX + "base-user-info-detail", key = "#userDetails.username", condition = "#userDetails.username!=null")
    public UserDetailVO getUserDetail(RichUserDetails userDetails) {
        assert userDetails != null;
        Optional<SystemUser> systemUserOptional = Optional.ofNullable(this.getById(userDetails.getUserId()));
        if (systemUserOptional.isPresent()) {
            SystemUser systemUser = systemUserOptional.get();
            Optional<UserInfoVO> userDetailOptional = Optional.ofNullable(ConverterUtil.convert(systemUser, new UserInfoVO()));
            UserInfoVO userInfoVO = userDetailOptional.orElseGet(UserInfoVO::new);
            UserDetailVO userDetailVO = new UserDetailVO();
            userDetailVO.setUser(userInfoVO);
            userDetailVO.setRoles(this.loadRoles(systemUser.getId()));
            userDetailVO.setPermissions(this.loadPermissions(systemUser.getId()));
            return userDetailVO;
        }
        throw new BaseRuntimeException("查询用户信息异常");
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = CacheConstant.INNER_API_PREFIX + "base-api-user", allEntries = true),
            @CacheEvict(value = CacheConstant.OUTER_API_PREFIX + "base-user-info-detail", key = "#userDetails.username", condition = "#userDetails.username!=null"),
            @CacheEvict(value = CacheConstant.OUTER_API_PREFIX + "base-user-detail", key = "#userDetails.userId", condition = "#userDetails.userId!=null")
    })
    public Boolean updateUserInfo(RichUserDetails userDetails, UserInfoVO info) {
        assert userDetails != null;
        String userId = userDetails.getUserId();
        Optional<SystemUser> systemUserOptional = Optional.ofNullable(ConverterUtil.convert(info, new SystemUser()));
        if (systemUserOptional.isPresent()) {
            SystemUser systemUser = systemUserOptional.get();
            systemUser.setId(userId);
            return this.updateById(systemUser);
        }
        throw new BaseRuntimeException("更新用户详情异常");
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = CacheConstant.INNER_API_PREFIX + "base-api-user", allEntries = true),
            @CacheEvict(value = CacheConstant.OUTER_API_PREFIX + "base-user-info-detail", key = "#userDetails.username", condition = "#userDetails.username!=null"),
            @CacheEvict(value = CacheConstant.OUTER_API_PREFIX + "base-user-detail", key = "#userDetails.userId", condition = "#userDetails.userId!=null")
    })
    public Integer changePassword(RichUserDetails userDetails, ChangePasswordVO changePassword) {
        try {
            if (!changePassword.getNewPassword().equals(changePassword.getRepeatPassword())) {
                return 2;
            } else {
                Optional<SystemUser> systemUserOptional = Optional.ofNullable(this.getById(userDetails.getUserId()));
                if (systemUserOptional.isPresent()) {
                    SystemUser systemUser = systemUserOptional.get();
                    if (!passwordEncoder.matches(changePassword.getOldPassword(), EncryptionConstant.SIGNATURE + systemUser.getPassword())) {
                        return 3;
                    } else {
                        systemUser.setPassword(userPasswordEncoder.encode(changePassword.getNewPassword()));
                        systemUser.setModifier(userDetails.getUserId());
                        systemUser.setModifyTime(LocalDateTime.now());
                        if (!this.update(userDetails.getUserId(), systemUser)) {
                            return 0;
                        }
                    }
                }

            }
            return 1;
        } catch (Exception e) {
            throw new BaseRuntimeException("更新用户密码异常");
        }
    }

    @Override
    public Integer changeMobile(RichUserDetails userDetails, ChangeMobileVO changeMobile) {
        try {
            if (!sensitiveInfoCaptchaValidateService.validate(changeMobile.getMobileTel(), changeMobile.getCaptcha())) {
                return 2;
            } else {
                Optional<SystemUser> systemUserOptional = Optional.ofNullable(this.getById(userDetails.getUserId()));
                if (systemUserOptional.isPresent()) {
                    SystemUser systemUser = systemUserOptional.get();
                    systemUser.setMobileTel(changeMobile.getMobileTel());
                    systemUser.setModifier(userDetails.getUserId());
                    systemUser.setModifyTime(LocalDateTime.now());
                    if (!this.update(userDetails.getUserId(), systemUser)) {
                        return 0;
                    }
                }
            }
            return 1;
        } catch (Exception e) {
            throw new BaseRuntimeException("更新用户手机号码异常");
        }
    }

    @Override
    public Boolean checkLoginName(String loginName) {
        return ObjectUtil.isNotNull(this.getOne(Wrappers.<SystemUser>lambdaQuery().eq(SystemUser::getLoginName, loginName)));
    }

    @Override
    public Boolean checkMobile(String mobile) {
        return ObjectUtil.isNotNull(this.getOne(Wrappers.<SystemUser>lambdaQuery().eq(SystemUser::getMobileTel, mobile)));
    }


    /**
     * 读取用户的扩展信息
     *
     * @return 用户扩展信息
     */
    private SystemUserExtend queryUserExtend(String userId) {
        Optional<SystemUserExtend> userExtendOptional = Optional.ofNullable(systemUserExtendService.getOne(Wrappers.<SystemUserExtend>lambdaQuery().eq(SystemUserExtend::getUserId, userId)));
        return userExtendOptional.orElseGet(SystemUserExtend::new);
    }

    private List<String> loadRoles(String userId) {
        List<SystemRole> systemRoles = systemRoleService.findRoleByUserId(userId);
        Set<String> roles = new HashSet<>();
        systemRoles.forEach(role -> roles.add(role.getRole()));
        return new ArrayList<>(roles);
    }

    private List<String> loadAllRoles() {
        List<SystemRole> systemRoles = systemRoleService.list();
        Set<String> roles = new HashSet<>();
        systemRoles.forEach(role -> roles.add(role.getRole()));
        return new ArrayList<>(roles);
    }

    private List<String> loadPermissions(String userId) {
        List<SystemMenuResource> systemMenuResources = systemMenuResourceService.loadPermissionsByUserId(userId);
        Set<String> permissions = new HashSet<>();
        systemMenuResources.forEach(menu -> permissions.add(menu.getPermission()));
        return new ArrayList<>(permissions);
    }

    private List<String> loadAllPermissions() {
        List<SystemMenuResource> systemMenuResources = systemMenuResourceService.list(Wrappers.<SystemMenuResource>lambdaQuery().eq(SystemMenuResource::getPermissionType, 1));
        Set<String> permissions = new HashSet<>();
        systemMenuResources.forEach(menu -> permissions.add(menu.getPermission()));
        return new ArrayList<>(permissions);
    }

}
