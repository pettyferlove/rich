package com.github.rich.base.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
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
@ApiModel(value="ChangePasswordVO对象", description="变更密码")
public class ChangePasswordVO implements Serializable {
    private static final long serialVersionUID = 157652993416293114L;

    @NotNull(message = "原始密码不可为空")
    private String oldPassword;

    @NotNull(message = "新密码不可为空")
    private String newPassword;

    @NotNull(message = "重复新密码不可为空")
    private String repeatPassword;
}
