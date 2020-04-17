package com.github.rich.auth.config;

import com.github.rich.auth.granter.MobileTokenGranter;
import com.github.rich.auth.granter.WeChatTokenGranter;
import com.github.rich.auth.service.RichClientDetailsService;
import com.github.rich.common.core.constants.CommonConstant;
import com.github.rich.security.component.ResponseExceptionTranslator;
import com.github.rich.security.service.CaptchaValidateService;
import com.github.rich.security.service.RichUserDetailsService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenApprovalStore;
import org.springframework.security.oauth2.provider.client.ClientCredentialsTokenGranter;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeTokenGranter;
import org.springframework.security.oauth2.provider.implicit.ImplicitTokenGranter;
import org.springframework.security.oauth2.provider.password.ResourceOwnerPasswordTokenGranter;
import org.springframework.security.oauth2.provider.refresh.RefreshTokenGranter;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.*;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Petty
 * @date 2018年12月6日
 * @see org.springframework.security.oauth2.provider.client.JdbcClientDetailsService
 * 用户认证服务
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    private static final String CLIENT_FIELDS_FOR_UPDATE = "resource_ids, scope, "
            + "authorized_grant_types, web_server_redirect_uri, authorities, access_token_validity, "
            + "refresh_token_validity, additional_information, autoapprove";

    private static final String CLIENT_FIELDS = "CONCAT('{noop}',client_secret) as client_secret, " + CLIENT_FIELDS_FOR_UPDATE;

    private static final String BASE_FIND_STATEMENT = "select client_id, " + CLIENT_FIELDS
            + " from system_oauth_client_details";

    private static final String DEFAULT_FIND_STATEMENT = BASE_FIND_STATEMENT + " order by client_id";

    private static final String DEFAULT_SELECT_STATEMENT = BASE_FIND_STATEMENT + " where client_id = ?";

    private final AuthenticationManager authenticationManager;

    private final RichUserDetailsService userDetailsService;

    private final ClientDetailsService clientDetailsService;

    private final RedisConnectionFactory redisConnectionFactory;

    private final CaptchaValidateService captchaValidateService;

    private final DataSource dataSource;

    @Autowired
    public AuthorizationServerConfig(DataSource dataSource, RichUserDetailsService userDetailsService, ClientDetailsService clientDetailsService, RedisConnectionFactory redisConnectionFactory, CaptchaValidateService smsCaptchaValidateService, AuthenticationManager authenticationManager) {
        this.dataSource = dataSource;
        this.userDetailsService = userDetailsService;
        this.clientDetailsService = clientDetailsService;
        this.redisConnectionFactory = redisConnectionFactory;
        this.captchaValidateService = smsCaptchaValidateService;
        this.authenticationManager = authenticationManager;
    }

    @Override
    @SneakyThrows
    public void configure(ClientDetailsServiceConfigurer clients) {
        RichClientDetailsService clientDetailsService = new RichClientDetailsService(dataSource);
        clientDetailsService.setSelectClientDetailsSql(DEFAULT_SELECT_STATEMENT);
        clientDetailsService.setFindClientDetailsSql(DEFAULT_FIND_STATEMENT);
        clients.withClientDetails(clientDetailsService);
    }

    private OAuth2RequestFactory requestFactory() {
        return new DefaultOAuth2RequestFactory(clientDetailsService);
    }

    @Bean
    @Primary
    public DefaultTokenServices tokenServices() {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setClientDetailsService(clientDetailsService);
        defaultTokenServices.setTokenStore(tokenStore());
        defaultTokenServices.setSupportRefreshToken(true);
        defaultTokenServices.setReuseRefreshToken(false);
        //defaultTokenServices.setTokenEnhancer(customerEnhancer());
        return defaultTokenServices;
    }

    /**
     * 将自定义Granter加入至授权池中
     *
     * @param authorizationCodeServices 默认初始化的AuthorizationCodeServices，不要自己初始化，会导致会在两个Service，直接导致AuthorizationCode模式失效
     * @return List
     */
    private List<TokenGranter> getDefaultTokenGranters(AuthorizationCodeServices authorizationCodeServices) {
        ClientDetailsService clientDetails = clientDetailsService;
        AuthorizationServerTokenServices tokenServices = tokenServices();
        OAuth2RequestFactory requestFactory = requestFactory();

        List<TokenGranter> tokenGranters = new ArrayList<>();
        tokenGranters.add(new AuthorizationCodeTokenGranter(tokenServices,
                authorizationCodeServices, clientDetails, requestFactory));
        tokenGranters.add(new RefreshTokenGranter(tokenServices, clientDetails, requestFactory));
        tokenGranters.add(new MobileTokenGranter(tokenServices, clientDetails, requestFactory, userDetailsService, captchaValidateService));
        tokenGranters.add(new WeChatTokenGranter(tokenServices, clientDetails, requestFactory, userDetailsService));
        ImplicitTokenGranter implicit = new ImplicitTokenGranter(tokenServices, clientDetails,
                requestFactory);
        tokenGranters.add(implicit);
        tokenGranters.add(
                new ClientCredentialsTokenGranter(tokenServices, clientDetails, requestFactory));
        if (authenticationManager != null) {
            tokenGranters.add(new ResourceOwnerPasswordTokenGranter(authenticationManager,
                    tokenServices, clientDetails, requestFactory));
        }
        return tokenGranters;
    }

    private TokenGranter tokenGranter(AuthorizationCodeServices authorizationCodeServices) {
        return new TokenGranter() {
            private CompositeTokenGranter delegate;

            @Override
            public OAuth2AccessToken grant(String grantType, TokenRequest tokenRequest) {
                if (delegate == null) {
                    delegate = new CompositeTokenGranter(getDefaultTokenGranters(authorizationCodeServices));
                }
                return delegate.grant(grantType, tokenRequest);
            }
        };
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        //Token存储位置
        endpoints.tokenStore(tokenStore());
        endpoints.authenticationManager(authenticationManager);
        endpoints.approvalStore(approvalStore());
        endpoints.userDetailsService(userDetailsService);
        endpoints.exceptionTranslator(new ResponseExceptionTranslator());
        endpoints.tokenGranter(tokenGranter(endpoints.getAuthorizationCodeServices()));
        super.configure(endpoints);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.allowFormAuthenticationForClients()
                .tokenKeyAccess("isAuthenticated()")
                .checkTokenAccess("permitAll()");
    }

    @Bean
    public TokenStore tokenStore() {
        return new RedisTokenStore(redisConnectionFactory);
    }

    @Bean
    public ApprovalStore approvalStore() {
        TokenApprovalStore store = new TokenApprovalStore();
        store.setTokenStore(tokenStore());
        return store;
    }

    /**
     * 注入自定义token生成方式
     *
     * @return TokenEnhancer
     */
    @Bean
    public TokenEnhancer customerEnhancer() {
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(new RichTokenEnhancer(), jwtAccessTokenConverter()));
        return tokenEnhancerChain;
    }


    /**
     * 配置Jwt转换器
     *
     * @return JwtAccessTokenConverter
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        final JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(CommonConstant.SIGN_KEY);
        //非对称加密配置
        //KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("mytest.jks"), "mypass".toCharArray());
        //converter.setKeyPair(keyStoreKeyFactory.getKeyPair("mytest"));
        converter.setAccessTokenConverter(new RichAccessTokenConverter());
        return converter;
    }
}
