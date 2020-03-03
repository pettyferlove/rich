package com.github.rich.base.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * 行政区划信息表
 * </p>
 *
 * @author Petty
 * @since 2019-06-26
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

    @ApiModelProperty(value = "行政区划编码")
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    @ApiModelProperty(value = "行政区划父级编码")
    private String parentId;

    @ApiModelProperty(value = "行政区划名称")
    private String regionName;

    @ApiModelProperty(value = "行政区划类型：0=国家；1=省；2=市；3=区／县")
    private Integer regionType;

    @ApiModelProperty(value = "是否最后一级区划")
    private Integer leaf;

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
    private LocalDateTime modifyTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
