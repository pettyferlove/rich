package com.github.rich.base.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.rich.base.dto.TreeNode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author Petty
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(value = "RegionNode对象")
public class RegionNode extends TreeNode {
    private static final long serialVersionUID = 1267865748975420444L;

    @ApiModelProperty(value = "行政区划编码")
    private String id;

    @ApiModelProperty(value = "行政区划父级编码")
    private String parentId;

    @ApiModelProperty(value = "行政区划名称")
    private String regionName;

    @ApiModelProperty(value = "行政区划类型：0=国家；1=省；2=市；3=区／县")
    private Integer regionType;


}
