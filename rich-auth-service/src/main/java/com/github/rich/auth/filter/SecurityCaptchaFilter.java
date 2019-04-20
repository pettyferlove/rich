package com.github.rich.auth.filter;

import com.github.rich.auth.config.CaptchaFilterConfig;
import com.github.rich.auth.service.CaptchaValidateService;
import com.github.rich.common.core.exception.security.CaptchaCheckException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Optional;

/**
 * 验证码校验
 *
 * @author Petty
 */
@Component
public class SecurityCaptchaFilter extends GenericFilterBean {

    private static final String OAUTH_TOKEN_URL = "/oauth/token";

    private static final String CAPTCHA_PARAM = "captcha";
    private static final String MACHINE_PARAM = "machine";
    private static final String GRANT_TYPE = "password";

    private final CaptchaFilterConfig captchaFilterConfig;

    private final RequestMatcher requestMatcher;

    private final CaptchaValidateService defaultCaptchaValidateService;

    @Autowired
    public SecurityCaptchaFilter(CaptchaFilterConfig captchaFilterConfig, CaptchaValidateService defaultCaptchaValidateService) {
        this.defaultCaptchaValidateService = defaultCaptchaValidateService;
        this.requestMatcher = new OrRequestMatcher(new AntPathRequestMatcher(OAUTH_TOKEN_URL, "GET"), new AntPathRequestMatcher(OAUTH_TOKEN_URL, "POST"));
        this.captchaFilterConfig = captchaFilterConfig;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        // 许可类型
        if (requestMatcher.matches(request)) {
            Optional<String> grantTypeOptional = Optional.ofNullable(request.getParameter("grant_type"));
            grantTypeOptional.orElseGet(String::new);
            if (grantTypeOptional.isPresent()) {
                if (GRANT_TYPE.toLowerCase().equals(grantTypeOptional.get().toLowerCase()) && captchaFilterConfig.getPassword()) {
                    // 拿到机器码
                    String machine = request.getParameter(MACHINE_PARAM);
                    // 拿到验证码
                    String captcha = request.getParameter(CAPTCHA_PARAM);
                    if (!defaultCaptchaValidateService.validate(machine, captcha)) {
                        throw new CaptchaCheckException("验证码错误");
                    }
                }
            }
        }
        chain.doFilter(req, res);
    }
}
