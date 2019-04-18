package com.github.rich.auth.exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

/**
 * 自定义OAuth2异常
 * @author Petty
 */
@JsonSerialize(using = RichOAuth2ExceptionJacksonSerializer.class)
public class RichOAuth2Exception extends OAuth2Exception {
    public RichOAuth2Exception(String msg, Throwable t) {
        super(msg, t);
    }

    public RichOAuth2Exception(String msg) {
        super(msg);
    }
}
