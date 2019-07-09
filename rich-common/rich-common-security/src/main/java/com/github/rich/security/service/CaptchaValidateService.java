package com.github.rich.security.service;

/**
 * 验证码校验
 * @author Petty
 */
public interface CaptchaValidateService {

    /**
     * 创建验证码
     * @param code 唯一码
     * @param captcha 验证码
     * @param timeout 有效期
     * @return True成功；False失败
     */
    Boolean create(String code, String captcha, long timeout);

    /**
     * 校验验证码
     * @param code 唯一码
     * @param captcha 验证码
     * @return True成功；False失败
     */
    Boolean validate(String code, String captcha);

    /**
     * 校验验证码
     * @param key Redis key
     * @param code 唯一码
     * @param captcha 验证码
     * @return True成功；False失败
     */
    Boolean validate(String key, String code, String captcha);
}
