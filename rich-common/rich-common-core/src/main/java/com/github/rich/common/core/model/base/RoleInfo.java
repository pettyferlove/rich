package com.github.rich.common.core.model.base;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 角色信息模型
 *
 * @author Petty
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RoleInfo implements Serializable {
    private static final long serialVersionUID = 4994910569599395548L;
    private String code;
    /**
     * 角色
     */
    private String role;
    /**
     * 角色名称
     */
    private String roleName;
    /**
     * 状态 1有效 0无效 默认为1
     */
    private String status;
}
