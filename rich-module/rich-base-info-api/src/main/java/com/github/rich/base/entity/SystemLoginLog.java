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
 * 用户登录日志
 * </p>
 *
 * @author Petty
 * @since 2019-05-22
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(value="SystemLoginLog对象", description="用户登录日志")
public class SystemLoginLog extends Model<SystemLoginLog> {

    private static final long serialVersionUID = 1L;

    private String id;

    @ApiModelProperty(value = "日志code")
    @TableId(value = "code", type = IdType.UUID)
    private String code;

    @ApiModelProperty(value = "用户登录名")
    private String loginCode;

    @ApiModelProperty(value = "用户账号")
    private String loginName;

    @ApiModelProperty(value = "登录时间")
    private LocalDateTime loginTime;

    @ApiModelProperty(value = "登录类型")
    private String loginType;

    @ApiModelProperty(value = "操作类型")
    private String operationType;

    @ApiModelProperty(value = "ip地址")
    private String loginIp;

    @ApiModelProperty(value = "所属操作系统")
    private String userAgent;

    @ApiModelProperty(value = "请求方法")
    private String requestMethod;

    @ApiModelProperty(value = "删除标记 0 未删除 1 删除")
    @TableLogic
    private Integer delFlag;

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
