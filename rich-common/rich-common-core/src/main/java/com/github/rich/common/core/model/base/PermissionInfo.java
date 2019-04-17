package com.github.rich.common.core.model.base;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Petty
 * @date 2018/12/26
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PermissionInfo implements Serializable {
    private static final long serialVersionUID = 7593984561438329435L;
    /**
     * 资源CODE
     */
    private String code;
    /**
     * 资源名称
     */
    private String permissionName;
    /**
     * 模块类型
     */
    private String moduleType;
    /**
     * 请求地址
     */
    private String requestUrl;
    /**
     * 请求方法
     */
    private String requestMethod;
    /**
     * 备注
     */
    private String remark;
}
