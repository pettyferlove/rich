package com.github.rich.common.core.model.base;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 用户基础信息模型，用户获取用户基础信息
 *
 * @author Petty
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserInfo implements Serializable {
    private static final long serialVersionUID = 6703721639017427276L;
    /**
     * 用户名称/登录名称
     */
    private String userName;
    /**
     * 用户类型
     */
    private String userType;
    /**
     * 用户全名
     */
    private String fullName;
    /**
     * 性别
     */
    private String userSex;
    /**
     * 生日
     */
    private String userBorn;
    /**
     * 用户头像
     */
    private String userAvatar;
    /**
     * 电子邮件
     */
    private String email;
    /**
     * 移动电话
     */
    private String mobileTel;
    /**
     * 用户联系电话
     */
    private String userTel;
    /**
     * 用户证件类型
     */
    private String userIdenType;
    /**
     * 证件ID
     */
    private String userIden;
    /**
     * 是否有效
     */
    private String status;
}
