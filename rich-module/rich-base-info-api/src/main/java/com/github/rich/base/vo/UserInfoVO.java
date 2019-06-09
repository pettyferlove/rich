package com.github.rich.base.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Petty
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(value="UserInfoVO对象", description="个人信息详情")
public class UserInfoVO implements Serializable {
    private static final long serialVersionUID = 6478708997777401299L;

    @ApiModelProperty(value = "个人信息")
    private UserDetailVO user;

    @ApiModelProperty(value = "角色")
    private List<String> roles = new ArrayList<>();

    @ApiModelProperty(value = "资源标识")
    private List<String> permissions = new ArrayList<>();

}
