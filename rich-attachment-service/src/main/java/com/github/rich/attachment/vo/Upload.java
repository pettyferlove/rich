package com.github.rich.attachment.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.rich.attachment.constants.SecurityTypeEnum;
import com.github.rich.attachment.constants.StorageTypeEnum;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
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
@ApiModel(value = "附件上传信息", description = "附件上传信息VO")
public class Upload implements Serializable {

    private static final long serialVersionUID = 3346764162292691065L;
    /**
     * 文件组，分组区分
     */
    @NotNull
    private String group;

    /**
     * 安全类型
     */
    @NotNull
    private SecurityTypeEnum security;

    /**
     * 存储类型
     */
    @NotNull
    private StorageTypeEnum storage;

}
