package com.github.rich.common.core.exception.auth;

import com.github.rich.common.core.constant.CommonConstant;
import com.github.rich.common.core.exception.BaseRuntimeException;

public class UserLoginException extends BaseRuntimeException {
    private static final long serialVersionUID = 1743414082337284940L;

    public UserLoginException(String message) {
        super(message, CommonConstant.EX_USER_LOGIN);
    }
}
