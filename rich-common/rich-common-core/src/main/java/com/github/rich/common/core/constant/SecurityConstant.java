package com.github.rich.common.core.constant;

/**
 * @author Petty
 */
public interface SecurityConstant {
    /**
     * 角色前缀
     */
    String ROLE = "ROLE_";

    /**
     * 基础用户角色
     */
    String BASE_ROLE = "ROLE_USER";
    /**
     * 管理员角色
     */
    String ADMIN_ROLE = "ROLE_ADMIN";
    /**
     * 开发者角色
     */
    String DEVELOPER_ROLE = "ROLE_DEVELOPER";

    /**
     * 匿名角色
     */
    String ROLE_ANONYMOUS = "ROLE_ANONYMOUS";

    /**
     * 资源授权失败或没有授权记录
     */
    String AUTHORIZATION_FAILURES = "403 Forbidden";

    /**
     * token
     */
    String TOKEN_USER_DETAIL = "token-user-detail";


    /**
     * 默认验证码Redis储存Key
     */
    String DEFAULT_CAPTCHA_CODE_KEY = "default-captcha-code-key";

    String SMS_CAPTCHA_CODE_KEY = "sms-captcha-code-key";

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
