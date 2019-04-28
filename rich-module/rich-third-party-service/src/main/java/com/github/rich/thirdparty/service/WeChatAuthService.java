package com.github.rich.thirdparty.service;

import com.github.rich.common.core.exception.BaseRuntimeException;
import com.github.rich.thirdparty.model.WeChatCode2SessionCallback;
import com.github.rich.thirdparty.model.WeChatUserInfoDecryptedData;

/**
 * 微信认证
 * @author Petty
 */
public interface WeChatAuthService {


    /**
     * 通过JS_CODE获取OpenID Session_Key
     * @param jsCode 调用wx.login 获取的code
     * @return WeChatCode2SessionCallback
     * @throws BaseRuntimeException Exception
     */
    WeChatCode2SessionCallback code2Session(String jsCode) throws BaseRuntimeException;

    /**
     * 解密用户数据
     * @param jsCode 调用wx.login 获取的code
     * @param encryptedData 加密数据
     * @param iv 偏移量
     * @return WeChatUserInfoDecryptedData
     * @throws BaseRuntimeException Exception
     */
    WeChatUserInfoDecryptedData decryptedUserInfo(String jsCode, String encryptedData, String iv) throws BaseRuntimeException;
}
