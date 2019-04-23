package com.github.rich.base.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableLogic;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户信息
 * </p>
 *
 * @author Petty
 * @since 2019-04-23
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
     * QQ OpenID
     */
    private String qqOpenid;

    /**
     * 微信UnionID
     */
    private Integer wechatUnionid;

    /**
     * 微信OpenID
     */
    private String wechatOpenid;

    /**
     * 用户类型
     */
    private String userType;

    /**
     * 性别
     */
    private Integer userSex;

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
     * 居住地址
     */
    private String userAddress;

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
     * 行政区划CODE
     */
    private String regionCode;

    /**
     * 是否有效 0 无效 1 有效
     */
    private Integer status;

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


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
