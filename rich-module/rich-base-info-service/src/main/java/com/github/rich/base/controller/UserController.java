package com.github.rich.base.controller;

import com.github.rich.base.service.ISystemUserService;
import com.github.rich.common.core.model.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Petty
 */
@Api(value = "用户信息", tags = {"用户信息接口"})
@RestController
@RequestMapping("/user")
public class UserController {

    private final ISystemUserService systemUserService;

    public UserController(ISystemUserService systemUserService) {
        this.systemUserService = systemUserService;
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
    public R<Boolean> registerByWeChatOpenID(String openid, String unionid) {
        openid = StringUtils.isBlank(openid) ? "" : openid;
        unionid = StringUtils.isBlank(unionid) ? "" : unionid;
        return new R<>(systemUserService.registerByWeChatOpenID(openid, unionid));
    }

}
