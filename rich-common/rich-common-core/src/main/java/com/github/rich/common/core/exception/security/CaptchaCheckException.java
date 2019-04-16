package com.github.rich.common.core.exception.security;

/**
 * @author Petty
 */
public class CaptchaCheckException extends RuntimeException {
    private static final long serialVersionUID = -2260108296379732094L;

    public CaptchaCheckException() {
    }

    public CaptchaCheckException(String message) {
        super(message);
    }

    public CaptchaCheckException(Throwable cause) {
        super(cause);
    }

    public CaptchaCheckException(String message, Throwable cause) {
        super(message, cause);
    }

    public CaptchaCheckException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
