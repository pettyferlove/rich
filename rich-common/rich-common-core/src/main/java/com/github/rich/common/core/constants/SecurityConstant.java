package com.github.rich.common.core.constants;

/**
 * @author Petty
 */
public interface SecurityConstant {

    /**
     * 角色前缀
     */
    String ROLE_PREFIX = "ROLE_";

    /**
     * 基础用户角色
     */
    String BASE_ROLE = "ROLE_USER";

    /**
     * 管理员角色
     */
    String SUPER_ROLE = "ROLE_ADMIN";

    /**
     * 超级管理员角色
     */
    String SUPER_ADMIN_ROLE = "ROLE_SUPER_ADMIN";
    /**
     * 开发者角色
     */
    String DEVELOPER_ROLE = "ROLE_DEVELOPER";

    /**
     * 匿名角色
     */
    String ROLE_ANONYMOUS = "ROLE_ANONYMOUS";
    /**
     * oauth 授权码模式
     */
    String AUTHORIZATION_CODE = "authorization_code";

    /**
     * 资源授权失败或没有授权记录
     */
    String AUTHORIZATION_FAILURES = "403 Forbidden";

    /**
     * 客户端授权模式
     */
    String CLIENT = "client_credentials";
    /**
     * 密码模式
     */
    String PASSWORD = "password";
    /**
     * 刷新token
     */
    String REFRESH_TOKEN = "refresh_token";

    /**
     * 手机登陆
     */
    String MOBILE = "mobile";

    String WECHAT = "wechat";
    /**
     * token
     */
    String TOKEN_USER_DETAIL = "token-user-detail";


    /**
     * 默认验证码Redis储存Key
     */
    String DEFAULT_CAPTCHA_CODE_KEY = "default-captcha-code-key";

    String LOGIN_SMS_CAPTCHA_CODE_KEY = "login-sms-captcha-code-key";

    String SENSITIVE_INFO_CAPTCHA_CODE_KEY = "sensitive-info-captcha-code-key";

    /**
     * 验证码过期时间
     */
    Integer VALIDATE_CODE_EXPIRY = 60;

    Integer SMS_VALIDATE_CODE_EXPIRY = 300;

    /**
     * 匿名用户
     */
    String ANONYMOUS_USER = "anonymousUser";

    String CLIENT_DETAILS_KEY = "oauth_client:details";
}
