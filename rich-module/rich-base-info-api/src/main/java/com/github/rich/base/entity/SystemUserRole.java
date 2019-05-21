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
 * 用户角色关联信息
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
@ApiModel(value="SystemUserRole对象", description="用户角色关联信息")
public class SystemUserRole extends Model<SystemUserRole> {

    private static final long serialVersionUID = 1L;

    private String id;

    @TableId(value = "code", type = IdType.UUID)
    private String code;

    private String userCode;

    private String roleCode;

    @ApiModelProperty(value = "客户端ID")
    private String client;


    @Override
    protected Serializable pkVal() {
        return this.code;
    }

}
