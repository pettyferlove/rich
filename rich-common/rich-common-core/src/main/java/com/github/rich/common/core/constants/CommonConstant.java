package com.github.rich.common.core.constants;


/**
 * @author Petty
 */
public interface CommonConstant {

    /**
     * 系统默认用户ID
     */
    String SYSTEM_USER_ID = "system";

    String INNER_SERVICE_PREFIX = "/api/inner/v1";

    String OUTER_SERVICE_PREFIX = "/api/v1";

    String OUTER_SERVICE_PREFIX_REGEX = OUTER_SERVICE_PREFIX + "/.[0-9a-zA-Z_]*/";


    String SUCCESS_MESSAGE = "Success";

    String FAIL_MESSAGE = "Fail";

    /**
     * WebSocket 处理成功
     */
    int WS_SUCCESS = 200001;

    /**
     * WebSocket 订阅成功
     */
    int WS_WELCOME = 200000;

    /**
     * WebSocket 处理失败
     */
    int WS_FAIL = 200005;


    /**
     * Jwt加密/解密凭据
     */
    String SIGN_KEY = "rich";
    /**
     * 解析Token分隔符
     */
    String TOKEN_SPLIT = "Bearer ";
    /**
     * 请求头
     */
    String REQUEST_HEADER = "Authorization";
    /**
     * 用户无效
     */
    int STATUS_INVALID = 0;
    /**
     * 用户正常
     */
    int STATUS_NORMAL = 1;
    /**
     * 用户锁定
     */
    int STATUS_LOCK = 9;
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
    String BASE_USER_CONTEXT = "base_user_context";

    /**
     * 未知客户端请求来源IP
     */
    String UN_KNOWN_CLIENT_IP = "unKnown";

    /**
     * 路由更新成功
     */
    int GATEWAY_ROUTE_UPDATE_OK = 1;

    /**
     * 路由添加成功
     */
    int GATEWAY_ROUTE_ADD_OK = 2;

    /**
     * 路由删除成功
     */
    int GATEWAY_ROUTE_DELETE_OK = 3;

    /**
     * 路由变更失败
     */
    int GATEWAY_ROUTE_CHANGE_FAIL = 4;
}
