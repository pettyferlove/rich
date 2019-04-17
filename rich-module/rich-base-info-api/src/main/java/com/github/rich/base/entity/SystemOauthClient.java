package com.github.rich.base.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 终端信息表
 * </p>
 *
 * @author Petty
 * @since 2019-04-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SystemOauthClient extends Model<SystemOauthClient> {

    private static final long serialVersionUID = 1L;

    private String clientId;

    private String resourceIds;

    private String clientSecret;

    private String scope;

    private String authorizedGrantTypes;

    private String webServerRedirectUri;

    private String authorities;

    private Integer accessTokenValidity;

    private Integer refreshTokenValidity;

    private String additionalInformation;

    private String autoapprove;


    @Override
    protected Serializable pkVal() {
        return this.clientId;
    }

}
