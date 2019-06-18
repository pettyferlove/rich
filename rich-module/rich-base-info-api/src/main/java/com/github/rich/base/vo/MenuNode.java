package com.github.rich.base.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.rich.base.dto.TreeNode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @author Petty
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(value="MenuNode对象")
public class MenuNode extends TreeNode {
    private static final long serialVersionUID = 8890625026946419517L;

    @ApiModelProperty(value = "Api资源code")
    private String id;

    @ApiModelProperty(value = "父级CODE")
    private String parentId;

    @ApiModelProperty(value = "菜单标题")
    private String title;

    @ApiModelProperty(value = "菜单图标")
    private String icon;

    @ApiModelProperty(value = "访问类型")
    private Integer visitType;

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

    @ApiModelProperty(value = "跳转页面地址")
    private String pageLink;

    @ApiModelProperty(value = "排序")
    private Long sort;

    @ApiModelProperty(value = "请求方法")
    private Integer permissionType;

    @ApiModelProperty(value = "资源")
    private String permission;
}
