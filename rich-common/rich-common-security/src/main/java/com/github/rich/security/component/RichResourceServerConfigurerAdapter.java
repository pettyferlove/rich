package com.github.rich.security.component;

import com.github.rich.common.core.config.FilterIgnoreProperties;
import com.github.rich.security.service.impl.RichRemoteTokenServices;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;
import org.springframework.web.client.RestTemplate;


/**
 * @author Petty
 */
@Slf4j
@EnableFeignClients(basePackageClasses = {com.github.rich.base.feign.RemoteUserService.class})
public class RichResourceServerConfigurerAdapter extends ResourceServerConfigurerAdapter {
    @Autowired
    protected ResourceAuthExceptionEntryPoint resourceAuthExceptionEntryPoint;
    @Autowired
    protected RichRemoteTokenServices richRemoteTokenServices;
    @Autowired
    private FilterIgnoreProperties filterIgnoreProperties;
    @Autowired
    private RestTemplate richRestTemplate;
    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * 将不需要鉴权的url加载到忽略链
     *
     * @param httpSecurity HttpSecurity
     */
    @Override
    @SneakyThrows
    public void configure(HttpSecurity httpSecurity) {
        //允许使用iframe 嵌套，避免swagger-ui 不被加载的问题
        httpSecurity.headers().frameOptions().disable();
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>
                .ExpressionInterceptUrlRegistry registry = httpSecurity
                .authorizeRequests();
        registry.antMatchers("/favicon.ico").permitAll();
        registry.antMatchers("/webjars/**").permitAll();
        registry.antMatchers("/images/**").permitAll();
        registry.antMatchers("/static/**").permitAll();
        registry.antMatchers("/swagger**/**").permitAll();
        registry.antMatchers("/swagger-resources/**").permitAll();
        registry.antMatchers("/swagger-ui.html").permitAll();
        registry.antMatchers("/**/v2/api-docs").permitAll();
        registry.antMatchers("/swagger/api-docs").permitAll();
        filterIgnoreProperties.getAnon()
                .forEach(url -> registry.antMatchers(url).permitAll());
        registry.anyRequest().authenticated()
                .and().csrf().disable();
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        DefaultAccessTokenConverter accessTokenConverter = new DefaultAccessTokenConverter();
        DefaultUserAuthenticationConverter userTokenConverter = new DefaultUserAuthenticationConverter();
        userTokenConverter.setUserDetailsService(userDetailsService);
        accessTokenConverter.setUserTokenConverter(userTokenConverter);
        richRemoteTokenServices.setRestTemplate(richRestTemplate);
        richRemoteTokenServices.setAccessTokenConverter(accessTokenConverter);
        resources.authenticationEntryPoint(resourceAuthExceptionEntryPoint)
                .tokenServices(richRemoteTokenServices);
    }
}
