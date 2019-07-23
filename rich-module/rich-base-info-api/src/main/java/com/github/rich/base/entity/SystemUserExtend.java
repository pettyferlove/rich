package com.github.rich.base.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * <p>
 * 用户信息扩展
 * </p>
 *
 * @author Petty
 * @since 2019-07-20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(value="SystemUserExtend对象", description="用户信息扩展")
public class SystemUserExtend extends Model<SystemUserExtend> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "数据唯一标识符")
    @TableId(value = "id", type = IdType.UUID)
    private String id;

    @ApiModelProperty(value = "用户ID")
    private String userId;

    @ApiModelProperty(value = "QQ OpenID")
    private String qqOpenid;

    @ApiModelProperty(value = "微信UnionID")
    private String wechatUnionid;

    @ApiModelProperty(value = "微信OpenID")
    private String wechatOpenid;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
