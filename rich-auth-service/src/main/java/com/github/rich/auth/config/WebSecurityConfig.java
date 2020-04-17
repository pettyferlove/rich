package com.github.rich.auth.config;

import com.github.rich.auth.handler.SuccessHandler;
import com.github.rich.security.service.RichUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.InMemoryTokenRepositoryImpl;

/**
 * 自定义OAuth2 登录页面入口配置
 *
 * @author Petty
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final RichUserDetailsService userDetailsService;

    private final PasswordEncoder passwordEncoder;

    public WebSecurityConfig(PasswordEncoder passwordEncoder, RichUserDetailsService userDetailsService) {
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
    }


    @Override
    public void configure(WebSecurity webSecurity) {
        //添加静态资源至过滤链
        webSecurity.ignoring().antMatchers("/webjars/**", "/images/**", "/static/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 表单登录
        http.formLogin().successHandler(new SuccessHandler())
                // 页面
                .loginPage("/login")
                // 登录处理url
                .loginProcessingUrl("/authorize")
                .and()
                .authorizeRequests()
                .antMatchers("/login").permitAll()
                .antMatchers("/**").authenticated()
                .and()
                //开启记住密码
                .rememberMe()
                .tokenRepository(tokenRepository())
                .tokenValiditySeconds(604800)
                .and()
                .csrf().disable();
        http.httpBasic().disable().csrf().disable();
        http.headers().frameOptions().disable();
    }

    @Bean
    public InMemoryTokenRepositoryImpl tokenRepository() {
        return new InMemoryTokenRepositoryImpl();
    }
}