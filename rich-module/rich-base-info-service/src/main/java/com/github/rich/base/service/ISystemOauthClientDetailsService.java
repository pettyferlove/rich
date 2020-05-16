package com.github.rich.base.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.rich.base.domain.dto.OAuthClientDetails;
import com.github.rich.base.entity.SystemOauthClientDetails;

/**
 * <p>
 * 终端信息表 服务类
 * </p>
 *
 * @author Petty
 * @since 2020-04-19
 */
public interface ISystemOauthClientDetailsService extends IService<SystemOauthClientDetails> {

    /**
     * 通过Id客户端信息
     *
     * @param clientId 客户端ID
     * @return SystemOauthClientDetails对象
     */
    OAuthClientDetails get(String clientId);

}
