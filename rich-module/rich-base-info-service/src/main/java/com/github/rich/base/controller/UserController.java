package com.github.rich.base.controller;

import com.github.rich.base.service.ISystemUserService;
import com.github.rich.common.core.model.R;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Petty
 */
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
    @PostMapping("/register/wechat/open")
    public R<Boolean> registerByWeChatOpenID(String openid, String unionid) {
        openid = StringUtils.isBlank(openid) ? "" : openid;
        unionid = StringUtils.isBlank(unionid) ? "" : unionid;
        return new R<>(systemUserService.registerByWeChatOpenID(openid, unionid));
    }

}
