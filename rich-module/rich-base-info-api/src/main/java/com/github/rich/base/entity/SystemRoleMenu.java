package com.github.rich.base.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.EqualsAndHashCode;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * <p>
 * 角色菜单关联
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
@ApiModel(value="SystemRoleMenu对象", description="角色菜单关联")
public class SystemRoleMenu extends Model<SystemRoleMenu> {

    private static final long serialVersionUID = 1L;

    private String id;

    @TableId(value = "code", type = IdType.UUID)
    private String code;

    private String roleCode;

    private String menuCode;

    @ApiModelProperty(value = "客户端ID")
    private String client;


    @Override
    protected Serializable pkVal() {
        return this.code;
    }

}