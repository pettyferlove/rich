package com.github.rich.base.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Petty
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    private static final long serialVersionUID = -2924404879928203710L;

    private String userCode;
    /**
     * 登录名
     */
    private String loginCode;

    /**
     * 用户名
     */
    private String userName;
    private String password;
    private Integer userType;
    private String email;
    private String mobileTel;
    private Integer status;
    /**
     * 角色
     */
    List<String> roles = new ArrayList<>();

    /**
     * 资源
     */
    List<String> permissions = new ArrayList<>();
}
