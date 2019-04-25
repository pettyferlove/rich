package com.github.rich.thirdparty.controller;

import com.github.rich.common.core.model.R;
import com.github.rich.thirdparty.model.WeChatAuthCallback;
import com.github.rich.thirdparty.service.WeChatAuthService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Petty
 */
@RestController
@RequestMapping("/wechat")
public class WeChatController {

    private final WeChatAuthService weChatAuthService;

    public WeChatController(WeChatAuthService weChatAuthService) {
        this.weChatAuthService = weChatAuthService;
    }

    @RequestMapping("/auth")
    public R<WeChatAuthCallback> auth(String code){
        return new R<>(weChatAuthService.auth(code));
    }
}
