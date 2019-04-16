package com.github.rich.common.core.exception.auth;


import com.github.rich.common.core.constant.CommonConstant;
import com.github.rich.common.core.exception.BaseRuntimeException;

/**
 * Token无效或者没有抛出该异常
 *
 * @author Petty
 * @version 1.0
 * {@link }
 */
public class TokenErrorException extends BaseRuntimeException {
    private static final long serialVersionUID = 8827019082477401466L;

    public TokenErrorException(String message) {
        super(message, CommonConstant.EX_TOKEN_ERROR_CODE);
    }
}
