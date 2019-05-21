package com.github.rich.base.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.EqualsAndHashCode;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * <p>
 * 用户信息
 * </p>
 *
 * @author Petty
 * @since 2019-05-21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(value="SystemUser对象", description="用户信息")
public class SystemUser extends Model<SystemUser> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "数据唯一标识符")
    private String id;

    @ApiModelProperty(value = "用户UUID")
    @TableId(value = "code", type = IdType.UUID)
    private String code;

    @ApiModelProperty(value = "用户登录名")
    private String loginCode;

    @ApiModelProperty(value = "用户全名")
    private String userName;

    @ApiModelProperty(value = "账号密码")
    private String password;

    @ApiModelProperty(value = "QQ OpenID")
    private String qqOpenid;

    @ApiModelProperty(value = "微信UnionID")
    private String wechatUnionid;

    @ApiModelProperty(value = "微信OpenID")
    private String wechatOpenid;

    @ApiModelProperty(value = "用户类型 0 管理员 1 普通用户")
    private Boolean userType;

    @ApiModelProperty(value = "性别")
    private Boolean userSex;

    @ApiModelProperty(value = "生日")
    private LocalDateTime userBorn;

    @ApiModelProperty(value = "用户头像")
    private String userAvatar;

    @ApiModelProperty(value = "电子邮件")
    private String email;

    @ApiModelProperty(value = "居住地址")
    private String userAddress;

    @ApiModelProperty(value = "移动电话")
    private String mobileTel;

    @ApiModelProperty(value = "用户联系电话")
    private String userTel;

    @ApiModelProperty(value = "用户证件类型")
    private String userIdenType;

    @ApiModelProperty(value = "证件ID")
    private String userIden;

    @ApiModelProperty(value = "行政区划CODE")
    private String regionCode;

    @ApiModelProperty(value = "是否有效 0 无效 1 有效")
    private Boolean status;

    @ApiModelProperty(value = "删除标记 0 未删除 1 删除")
    @TableLogic
    private Boolean delFlag;

    @ApiModelProperty(value = "创建人")
    private String creator;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改人")
    private String modifier;

    @ApiModelProperty(value = "修改时间")
    private LocalDateTime modifierTime;


    @Override
    protected Serializable pkVal() {
        return this.code;
    }

}
