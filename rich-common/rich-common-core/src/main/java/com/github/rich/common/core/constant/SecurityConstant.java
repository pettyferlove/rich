package com.github.rich.common.core.constant;

/**
 * @author Petty
 */
public interface SecurityConstant {
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

    String CLIENT_DETAILS_KEY = "oauth:client:details";

    /**
     * system_oauth_client 表的字段
     */
    String CLIENT_FIELDS = "client_id, client_secret, resource_ids, scope, "
            + "authorized_grant_types, web_server_redirect_uri, authorities, access_token_validity, "
            + "refresh_token_validity, additional_information, autoapprove";

    /**
     * JdbcClientDetailsService 查询语句
     */
    String BASE_FIND_STATEMENT = "select " + CLIENT_FIELDS
            + " from system_oauth_client";

    /**
     * 默认的查询语句
     */
    String DEFAULT_FIND_STATEMENT = BASE_FIND_STATEMENT + " order by client_id";

    /**
     * 按条件client_id 查询
     */
    String DEFAULT_SELECT_STATEMENT = BASE_FIND_STATEMENT + " where client_id = ?";


}
