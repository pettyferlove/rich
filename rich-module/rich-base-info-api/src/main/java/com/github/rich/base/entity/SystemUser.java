package com.github.rich.base.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableLogic;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户信息
 * </p>
 *
 * @author Petty
 * @since 2019-04-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SystemUser extends Model<SystemUser> {

    private static final long serialVersionUID = 1L;

    /**
     * 数据唯一标识符
     */
    private String id;

    /**
     * 用户名称/登录名称
     */
    @TableId
    private String userCode;

    /**
     * 用户全名
     */
    private String userName;

    /**
     * 账号密码
     */
    private String password;

    /**
     * 用户类型
     */
    private String userType;

    /**
     * 性别
     */
    private String userSex;

    /**
     * 生日
     */
    private LocalDateTime userBorn;

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

    /**
     * 删除标记 0 未删除 1 删除
     */
    @TableLogic
    private Integer delFlag;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改人
     */
    private String modifier;

    /**
     * 修改时间
     */
    private LocalDateTime modifierTime;

    /**
     * 行政区划CODE
     */
    private String areaCode;

    /**
     * 行政区划名称
     */
    private String areaName;

    /**
     * 部门CODE
     */
    private String deptCode;

    /**
     * 部门名称
     */
    private String deptName;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
