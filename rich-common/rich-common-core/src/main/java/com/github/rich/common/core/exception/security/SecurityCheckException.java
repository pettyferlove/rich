package com.github.rich.common.core.exception.security;

/**
 * 安全检查异常抛出
 * @author Petty
 * @since 2018-12-29
 */
public class SecurityCheckException extends RuntimeException{
    private static final long serialVersionUID = -6236390943332100757L;

    public SecurityCheckException() {
    }

    public SecurityCheckException(String message) {
        super(message);
    }

    public SecurityCheckException(Throwable cause) {
        super(cause);
    }

    public SecurityCheckException(String message, Throwable cause) {
        super(message, cause);
    }

    public SecurityCheckException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
