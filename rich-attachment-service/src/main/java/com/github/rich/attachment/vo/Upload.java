package com.github.rich.attachment.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.rich.attachment.validator.StorageTypeConstraint;
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
@ApiModel(value="附件上传信息", description="附件上传信息VO")
public class Upload implements Serializable {

    private static final long serialVersionUID = 3346764162292691065L;

    @NotNull
    private String name;

    @NotNull
    private String security;

    @StorageTypeConstraint
    private int storageType;

}
