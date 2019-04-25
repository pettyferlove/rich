package com.github.rich.thirdparty.service;

import com.github.rich.common.core.exception.BaseRuntimeException;
import com.github.rich.thirdparty.model.WeChatAuthCallback;

/**
 * 微信认证
 * @author Petty
 */
public interface WeChatAuthService {


    /**
     * 通过JS_CODE获取
     * @param jsCode 调用wx.login 获取的code
     * @return WeChatAuthCallback
     * @throws BaseRuntimeException Exception
     */
    WeChatAuthCallback auth(String jsCode) throws BaseRuntimeException;
}
