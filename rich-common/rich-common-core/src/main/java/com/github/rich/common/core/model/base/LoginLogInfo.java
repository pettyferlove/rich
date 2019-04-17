package com.github.rich.common.core.model.base;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Petty
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginLogInfo implements Serializable {
    private static final long serialVersionUID = 5896078457752529966L;

    /**
     * 账号
     */
    private String loginUser;

    /**
     * 用户名
     */
    private String loginName;

    /**
     * 登录类型
     */
    private String loginType;

    /**
     * 登录时间
     */
    private String loginDate;

    /**
     * 操作类型
     */
    private String operationType;

    /**
     * 登录IP
     */
    private String loginIp;

    /**
     * 用户代理
     */
    private String userAgent;

    /**
     * 请求方法
     */
    private String requestMethod;
}
