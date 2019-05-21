package com.github.rich.base.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.EqualsAndHashCode;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * <p>
 * 
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
@ApiModel(value="SystemMenuResource对象", description="")
public class SystemMenuResource extends Model<SystemMenuResource> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "数据唯一标识符")
    private String id;

    @ApiModelProperty(value = "Api资源code")
    @TableId(value = "code", type = IdType.UUID)
    private String code;

    @ApiModelProperty(value = "父级CODE")
    private String parentCode;

    @ApiModelProperty(value = "菜单标题")
    private String title;

    private String icon;

    @TableField("targetType")
    private Boolean targetType;

    private String target;

    private Boolean cache;

    @TableField("componentName")
    private String componentName;

    private Integer sort;

    @ApiModelProperty(value = "请求方法")
    @TableField("permissionType")
    private Boolean permissionType;

    @ApiModelProperty(value = "资源")
    private String permission;

    @ApiModelProperty(value = "状态 0 无效 1有效")
    private String status;

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

    @ApiModelProperty(value = "客户端ID")
    private String client;


    @Override
    protected Serializable pkVal() {
        return this.code;
    }

}
