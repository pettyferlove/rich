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
 * 
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
@ApiModel(value="SystemGatewayRoute对象", description="")
public class SystemGatewayRoute extends Model<SystemGatewayRoute> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "数据唯一标识符")
    private String id;

    @ApiModelProperty(value = "网关CODE")
    @TableId(value = "code", type = IdType.UUID)
    private String code;

    @ApiModelProperty(value = "Route JSON")
    private String route;

    @ApiModelProperty(value = "状态 1有效 0无效 默认为1")
    private Integer status;

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
