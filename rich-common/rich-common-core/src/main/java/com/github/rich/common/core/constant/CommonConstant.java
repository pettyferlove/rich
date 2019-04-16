package com.github.rich.common.core.constant;


/**
 * @author Petty
 */
public interface CommonConstant {

    /**
     * 用户未登陆
     */
    int NO_LOGIN = 403;

    int SUCCESS = 200;

    int FAIL = 500;

    int NO_PERMISSION = 403;

    /**
     * 没有权限
     */
    Integer EX_TOKEN_ERROR_CODE = 403;

    Integer EX_USER_LOGIN = 400;

    Integer EX_NO_USER = 405;

    Integer EX_OTHER_CODE = 500;
    /**
     * Jwt加密/解密凭据
     */
    String SIGN_KEY = "eda";
    /**
     * 解析Token分隔符
     */
    String TOKEN_SPLIT = "Bearer ";
    /**
     * 请求头
     */
    String REQUEST_HEADER = "Authorization";

    /**
     * 传递至服务的用户头
     */
    String REQUEST_USER_HEADER = "Eda-Request-User";
    /**
     * 传递至服务的角色头
     */
    String REQUEST_ROLE_HEADER = "Eda-Request-Role";
    /**
     * 用户无效
     */
    String STATUS_INVALID = "0";
    /**
     * 用户正常
     */
    String STATUS_NORMAL = "1";
    /**
     * 用户锁定
     */
    String STATUS_LOCK = "9";
    String DEL_FLAG = "del_flag";

    /**
     * 服务端返回上下文信息类型
     */
    String CONTENT_TYPE = "application/json; charset=utf-8";
    /**
     * 编码
     */
    String UTF8 = "UTF-8";

    /**
     * 用户名称上下文传递Map数据结构所需key
     */
    String BASIC_USER_CONTEXT = "basic_user_context";

    /**
     * 未知客户端请求来源IP
     */
    String UN_KNOWN_CLIENT_IP = "unKnown";
}
