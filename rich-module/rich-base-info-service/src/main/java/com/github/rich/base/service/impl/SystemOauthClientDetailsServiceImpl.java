package com.github.rich.base.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.rich.base.constants.CacheConstant;
import com.github.rich.base.domain.dto.OAuthClientDetails;
import com.github.rich.base.entity.SystemOauthClientDetails;
import com.github.rich.base.mapper.SystemOauthClientDetailsMapper;
import com.github.rich.base.service.ISystemOauthClientDetailsService;
import com.github.rich.common.core.utils.ConverterUtil;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * <p>
 * 终端信息表 服务实现类
 * </p>
 *
 * @author Petty
 * @since 2020-04-19
 */
@Service
public class SystemOauthClientDetailsServiceImpl extends ServiceImpl<SystemOauthClientDetailsMapper, SystemOauthClientDetails> implements ISystemOauthClientDetailsService {

    @Override
    @Cacheable(value = CacheConstant.INNER_API_PREFIX + "base-oauth-client-details", key = "#clientId", condition = "#clientId!=null")
    public OAuthClientDetails get(String clientId) {
        SystemOauthClientDetails systemOauthClientDetails = this.getById(clientId);
        Optional<OAuthClientDetails> oAuthClientDetailsOptional = Optional.ofNullable(ConverterUtil.convert(systemOauthClientDetails, new OAuthClientDetails()));
        return oAuthClientDetailsOptional.orElseGet(OAuthClientDetails::new);
    }

}
