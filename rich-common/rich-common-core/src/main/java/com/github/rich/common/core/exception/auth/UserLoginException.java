package com.github.rich.common.core.exception.auth;

import com.github.rich.common.core.constants.CommonConstant;
import com.github.rich.common.core.exception.BaseRuntimeException;

/**
 * @author Petty
 */
public class UserLoginException extends BaseRuntimeException {
    private static final long serialVersionUID = 1743414082337284940L;

    public UserLoginException(String message) {
        super(message, CommonConstant.EX_USER_LOGIN);
    }
}
