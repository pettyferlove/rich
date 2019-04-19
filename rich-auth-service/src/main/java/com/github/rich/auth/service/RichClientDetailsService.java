package com.github.rich.auth.service;

import com.github.rich.common.core.constant.SecurityConstant;
import lombok.SneakyThrows;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.oauth2.common.exceptions.InvalidClientException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;

import javax.sql.DataSource;

/**
 * @author Petty
 */
public class RichClientDetailsService extends JdbcClientDetailsService {

    public RichClientDetailsService(DataSource dataSource) {
        super(dataSource);
    }

    /**
     * 重写接口但是不额外增加逻辑，增加缓存即可
     * @param clientId 客户端ID
     * @return ClientDetails
     * @throws InvalidClientException 客户端无效异常
     */
    @Cacheable(value = SecurityConstant.CLIENT_DETAILS_KEY, key = "#clientId", unless = "#result == null")
    @Override
    @SneakyThrows
    public ClientDetails loadClientByClientId(String clientId) throws InvalidClientException {
        return super.loadClientByClientId(clientId);
    }
}
