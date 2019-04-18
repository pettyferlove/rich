package com.github.rich.security.service;

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

    @SneakyThrows
    @Cacheable(value = SecurityConstant.CLIENT_DETAILS_KEY, key = "#clientId", unless = "#result == null")
    @Override
    public ClientDetails loadClientByClientId(String clientId) throws InvalidClientException {
        return super.loadClientByClientId(clientId);
    }
}
