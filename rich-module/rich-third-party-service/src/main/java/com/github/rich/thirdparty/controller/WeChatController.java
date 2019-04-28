package com.github.rich.thirdparty.controller;

import com.github.rich.common.core.model.R;
import com.github.rich.thirdparty.model.WeChatCode2SessionCallback;
import com.github.rich.thirdparty.model.WeChatUserInfoDecryptedData;
import com.github.rich.thirdparty.service.WeChatAuthService;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Petty
 */
@Api(value = "微信信息相关接口", tags = {"微信信息接口"})
@RestController
@RequestMapping("/wechat")
public class WeChatController {

    private final WeChatAuthService weChatAuthService;

    public WeChatController(WeChatAuthService weChatAuthService) {
        this.weChatAuthService = weChatAuthService;
    }

    @ApiOperation(value = "获取微信OpenID", notes = "无需权限")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", name = "code", value = "JS_CODE", dataTypeClass = String.class),
    })
    @PostMapping("/auth/openid")
    public R<WeChatCode2SessionCallback> auth(String code) {
        return new R<>(weChatAuthService.code2Session(code));
    }

    @ApiOperation(value = "获取微信详细用户信息", notes = "无需权限")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "code", name = "code", value = "JS_CODE", dataTypeClass = String.class),
            @ApiImplicitParam(paramType = "encryptedData", name = "encryptedData", value = "加密数据", dataTypeClass = String.class),
            @ApiImplicitParam(paramType = "iv", name = "iv", value = "偏移量", dataTypeClass = String.class),
    })
    @PostMapping("/auth/user/info")
    public R<WeChatUserInfoDecryptedData> userInfo(String code, String encryptedData, String iv) {
        return new R<>(weChatAuthService.decryptedUserInfo(code, encryptedData, iv));
    }
}
