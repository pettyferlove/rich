package com.github.rich.common.core.exception.access;

/**
 * @author lengleng
 * @date 2017-12-29 17:05:10
 * 403 授权拒绝
 */
public class DeniedException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public DeniedException() {
    }

    public DeniedException(String message) {
        super(message);
    }

    public DeniedException(Throwable cause) {
        super(cause);
    }

    public DeniedException(String message, Throwable cause) {
        super(message, cause);
    }

    public DeniedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
