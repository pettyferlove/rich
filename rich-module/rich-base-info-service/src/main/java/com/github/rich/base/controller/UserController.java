package com.github.rich.base.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.rich.base.entity.SystemUser;
import com.github.rich.base.service.ISystemUserService;
import com.github.rich.base.vo.ChangeMobileVO;
import com.github.rich.base.vo.ChangePasswordVO;
import com.github.rich.base.vo.UserDetailVO;
import com.github.rich.base.vo.UserInfoVO;
import com.github.rich.common.core.constants.SecurityConstant;
import com.github.rich.common.core.utils.SMSUtil;
import com.github.rich.common.core.vo.R;
import com.github.rich.log.annotation.UserLog;
import com.github.rich.log.constants.OperateType;
import com.github.rich.security.service.CaptchaValidateService;
import com.github.rich.security.utils.SecurityUtil;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * @author Petty
 */
@Api(value = "用户信息", tags = {"用户信息接口"})
@RestController
@RequestMapping("/user")
public class UserController {

    private final ISystemUserService systemUserService;

    private final CaptchaValidateService sensitiveInfoCaptchaValidateService;

    public UserController(ISystemUserService systemUserService, CaptchaValidateService sensitiveInfoCaptchaValidateService) {
        this.systemUserService = systemUserService;
        this.sensitiveInfoCaptchaValidateService = sensitiveInfoCaptchaValidateService;
    }

    /**
     * 通过OpenID UnionID快速注册
     *
     * @param openid  微信OpenID
     * @param unionid 微信UnionID
     * @return 是否成功 True 是 False 否
     */
    @ApiOperation(value = "通过OpenID快速注册用户信息", notes = "无需权限")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", name = "openid", value = "OpenID", dataTypeClass = String.class),
            @ApiImplicitParam(paramType = "form", name = "unionid", value = "UnionID", dataTypeClass = String.class)
    })
    @PostMapping("/register/wechat/open")
    public R<Boolean> registerByWeChat(String openid, String unionid) {
        openid = StringUtils.isBlank(openid) ? "" : openid;
        unionid = StringUtils.isBlank(unionid) ? "" : unionid;
        return new R<>(systemUserService.registerByWeChat(openid, unionid));
    }

    @ApiOperation(value = "获取用户列表", notes = "无需特殊权限", authorizations = @Authorization(value = "oauth2"))
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "systemUser", value = "SystemUser", dataTypeClass = SystemUser.class),
            @ApiImplicitParam(paramType = "query", name = "page", value = "Page", dataTypeClass = Page.class)
    })
    @GetMapping("page")
    public R<IPage> page(SystemUser systemUser, Page<SystemUser> page) {
        return new R<>(systemUserService.page(systemUser, page));
    }

    @ApiOperation(value = "获取用户信息", notes = "无需特殊权限", authorizations = @Authorization(value = "oauth2"))
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "id", value = "id", dataTypeClass = String.class)
    })
    @GetMapping("/{id}")
    public R<SystemUser> get(@PathVariable String id) {
        return new R<>(systemUserService.get(id));
    }

    @ApiOperation(value = "创建用户", notes = "需要管理员权限", authorizations = @Authorization(value = "oauth2"))
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "systemUser", value = "SystemUser", dataTypeClass = SystemUser.class)
    })
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN')")
    @PostMapping
    @UserLog(type = OperateType.ADD, description = "创建用户")
    public R<String> create(SystemUser systemUser) {
        return new R<>(systemUserService.create(Objects.requireNonNull(SecurityUtil.getUser()).getUserId(), systemUser));
    }

    @ApiOperation(value = "更新用户", notes = "需要管理员权限", authorizations = @Authorization(value = "oauth2"))
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "systemUser", value = "SystemUser", dataTypeClass = SystemUser.class)
    })
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN')")
    @PutMapping
    @UserLog(type = OperateType.UPDATE, description = "更新用户")
    public R<Boolean> update(SystemUser systemUser) {
        String superAdminRole = "SUPER_ADMIN";
        if (!SecurityUtil.getRoles().contains(superAdminRole)) {
            /*
              超级管理员更新数据部要更新租户ID
              设置为空值将指不更新数据库字段
             */
            systemUser.setTenantId(null);
        }
        return new R<>(systemUserService.update(Objects.requireNonNull(SecurityUtil.getUser()).getUserId(), systemUser));
    }

    @ApiOperation(value = "删除用户", notes = "删除用户将会删除与用户相关的关联数据，同时需要管理员权限", authorizations = @Authorization(value = "oauth2"))
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "id", value = "id", dataTypeClass = String.class)
    })
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN')")
    @DeleteMapping("/{id}")
    @UserLog(type = OperateType.DELETE, description = "删除用户")
    public R<Boolean> delete(@PathVariable String id) {
        return new R<>(systemUserService.delete(id));
    }

    @ApiOperation(value = "获取个人信息", notes = "无需特殊权限", authorizations = @Authorization(value = "oauth2"))
    @GetMapping("/info")
    public R<UserDetailVO> getInfo() {
        return new R<>(systemUserService.getUserDetail(SecurityUtil.getUser()));
    }

    @ApiOperation(value = "更新个人信息", notes = "无需特殊权限", authorizations = @Authorization(value = "oauth2"))
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "info", value = "info", dataTypeClass = UserInfoVO.class)
    })
    @PutMapping("/info")
    @UserLog(type = OperateType.UPDATE, description = "更新个人信息-用户个人操作")
    public R<Boolean> updateInfo(UserInfoVO info) {
        return new R<>(systemUserService.updateUserInfo(SecurityUtil.getUser(), info));
    }


    @ApiOperation(value = "更新密码", notes = "无需特殊权限", authorizations = @Authorization(value = "oauth2"))
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "changePassword", value = "changePassword", dataTypeClass = ChangePasswordVO.class)
    })
    @PutMapping("/change/password")
    @UserLog(type = OperateType.UPDATE, description = "更新密码-用户个人操作")
    public R<Integer> changePassword(@Validated ChangePasswordVO changePassword) {
        return new R<>(systemUserService.changePassword(SecurityUtil.getUser(), changePassword));
    }

    @ApiOperation(value = "更新手机号码", notes = "无需特殊权限", authorizations = @Authorization(value = "oauth2"))
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "changeMobile", value = "changeMobile", dataTypeClass = ChangeMobileVO.class)
    })
    @PutMapping("/change/mobile")
    @UserLog(type = OperateType.UPDATE, description = "更新手机号码-用户个人操作")
    public R<Integer> changeMobile(@Validated ChangeMobileVO changeMobile) {
        return new R<>(systemUserService.changeMobile(SecurityUtil.getUser(), changeMobile));
    }

    @ApiOperation(value = "检测登录名是否存在", notes = "无需特殊权限", authorizations = @Authorization(value = "oauth2"))
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "loginName", value = "loginName", dataTypeClass = String.class)
    })
    @GetMapping("/check/name/{loginName}")
    public R<Boolean> checkLoginName(@PathVariable String loginName) {
        return new R<>(systemUserService.checkLoginName(loginName));
    }

    @ApiOperation(value = "检测手机号码是否存在", notes = "无需特殊权限", authorizations = @Authorization(value = "oauth2"))
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "mobile", value = "mobile", dataTypeClass = String.class)
    })
    @GetMapping("/check/mobile/{mobile}")
    public R<Boolean> checkMobile(@PathVariable String mobile) {
        return new R<>(systemUserService.checkMobile(mobile));
    }


    @ApiOperation(value = "创建短信验证码", notes = "用于用户更改手机，无需特殊权限", authorizations = @Authorization(value = "oauth2"))
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "mobileTel", value = "mobileTel", dataTypeClass = String.class)
    })
    @PostMapping("/captcha/mobile/{mobileTel}")
    public R<Boolean> mobileCaptcha(@PathVariable String mobileTel) {
        String randomStr = SMSUtil.createRandomVcode();
        return new R<>(sensitiveInfoCaptchaValidateService.create(mobileTel, randomStr, SecurityConstant.SMS_VALIDATE_CODE_EXPIRY));
    }

}
