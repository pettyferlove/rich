package com.github.rich.base.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.github.rich.common.core.datatype.CustomLocalDateTimeDeserializer;
import com.github.rich.common.core.datatype.CustomLocalDateTimeSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * 系统菜单
 * </p>
 *
 * @author Petty
 * @since 2019-06-10
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(value="SystemMenuResource对象", description="系统菜单")
public class SystemMenuResource extends Model<SystemMenuResource> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "Api资源code")
    private String id;

    @ApiModelProperty(value = "父级CODE")
    private String parentId;

    @ApiModelProperty(value = "菜单标题")
    private String title;

    @ApiModelProperty(value = "菜单图标")
    private String icon;

    @ApiModelProperty(value = "路由名称")
    private String routeName;

    @ApiModelProperty(value = "是否可以加入面包屑")
    private Integer breadcrumb;

    @ApiModelProperty(value = "路由组件名")
    private String componentName;

    @ApiModelProperty(value = "路由组件路径")
    private String componentPath;

    @ApiModelProperty(value = "路由地址")
    private String path;

    @ApiModelProperty(value = "路由重定向地址")
    private String redirectPath;

    @ApiModelProperty(value = "是否由前端缓存")
    private Integer keepAlive;

    @ApiModelProperty(value = "排序")
    private Long sort;

    @ApiModelProperty(value = "请求方法")
    private Integer permissionType;

    @ApiModelProperty(value = "资源")
    private String permission;

    @ApiModelProperty(value = "删除标记 0 未删除 1 删除")
    @TableLogic
    private Integer delFlag;

    @ApiModelProperty(value = "创建人")
    private String creator;

    @ApiModelProperty(value = "创建时间")
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @DateTimeFormat( pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改人")
    private String modifier;

    @ApiModelProperty(value = "修改时间")
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @DateTimeFormat( pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modifierTime;


    @Override
    protected Serializable pkVal() {
        return null;
    }

}
