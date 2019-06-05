package com.github.rich.attachment.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Petty
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(value="附件上传结果", description="附件上传结果VO")
public class UploadResult implements Serializable {
    private static final long serialVersionUID = 6121441712776319710L;

    @ApiModelProperty(value = "附件ID")
    private String fileId;

    @ApiModelProperty(value = "附件名")
    private String fileName;

    @ApiModelProperty(value = "存储类型")
    private String storeType;

    @ApiModelProperty(value = "地址")
    private String path;

}
