package com.github.rich.common.core.exception.auth;


import com.github.rich.common.core.constants.CommonConstant;
import com.github.rich.common.core.exception.BaseRuntimeException;

/**
 * 没有找到从Header中解析出来的用户信息时，抛出该异常
 * @author Petty
 * @version 1.0
 * {@link  }
 */
public class UserContextException extends BaseRuntimeException {

    private static final long serialVersionUID = 1743414082337284940L;

    public UserContextException(String message) {
        super(message, CommonConstant.EX_NO_USER);
    }
}
