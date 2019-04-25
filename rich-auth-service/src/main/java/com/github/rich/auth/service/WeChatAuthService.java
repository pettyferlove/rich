package com.github.rich.auth.service;

/**
 * 微信认证
 * @author Petty
 */
public interface WeChatAuthService {


    /**
     * 通过JS_CODE获取
     * @param jsCode 调用wx.login 获取的code
     * @return
     */
    String auth(String jsCode);
}
