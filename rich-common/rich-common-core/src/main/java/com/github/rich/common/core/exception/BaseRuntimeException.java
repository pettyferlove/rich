package com.github.rich.common.core.exception;


import lombok.*;

import java.io.Serializable;

/**
 * 自定义RuntimeException
 *
 * @author Petty
 * @date 2018年2月24日
 */
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BaseRuntimeException extends RuntimeException implements Serializable {
    private static final long serialVersionUID = 5785773026238677601L;
    private int status = 500;

    public BaseRuntimeException(String message, int status) {
        super(message);
        this.status = status;
    }

    public BaseRuntimeException(String message) {
        super(message);
    }

    public BaseRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public BaseRuntimeException(Throwable cause) {
        super(cause);
    }

    public BaseRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
