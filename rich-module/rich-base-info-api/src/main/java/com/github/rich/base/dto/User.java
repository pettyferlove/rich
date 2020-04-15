package com.github.rich.base.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Petty
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class User implements Serializable {
    private static final long serialVersionUID = -2924404879928203710L;

    private String id;
    /**
     * 登录名
     */
    private String loginName;

    /**
     * 用户名
     */
    private String userName;
    private String password;
    private Integer status;
    /**
     * 用户类型
     */
    private Integer userType;

    /**
     * 租户ID
     */
    private String tenantId;

    /**
     * 用户所属行政区划
     */
    private Integer region;
    /**
     * 角色
     */
    List<String> roles = new ArrayList<>();

    /**
     * 资源
     */
    List<String> permissions = new ArrayList<>();
}
