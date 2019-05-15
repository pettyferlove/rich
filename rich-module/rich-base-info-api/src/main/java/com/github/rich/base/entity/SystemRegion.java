package com.github.rich.base.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
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
 * 行政区划信息表
 * </p>
 *
 * @author Petty
 * @since 2019-05-15
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(value="SystemRegion对象", description="行政区划信息表")
public class SystemRegion extends Model<SystemRegion> {

    private static final long serialVersionUID = 1L;

    private String id;

    @ApiModelProperty(value = "行政区划编码")
    @TableId(value = "code", type = IdType.UUID)
    private String code;

    @ApiModelProperty(value = "行政区划父级编码")
    private String parentCode;

    @ApiModelProperty(value = "行政区划名称")
    private String regionName;

    @ApiModelProperty(value = "行政区划类型：0=国家；1=省；2=市；3=区／县")
    private Integer regionType;

    @ApiModelProperty(value = "删除标记")
    @TableLogic
    private Integer delFlag;

    @ApiModelProperty(value = "客户端ID")
    private String client;


    @Override
    protected Serializable pkVal() {
        return this.code;
    }

}
