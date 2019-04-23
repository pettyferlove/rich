package com.github.rich.base.api.impl;

import com.github.rich.base.api.UserServiceApi;
import com.github.rich.base.model.User;
import com.github.rich.base.service.ISystemUserService;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Petty
 */
@RestController
public class UserServiceApiImpl implements UserServiceApi {

    private final ISystemUserService systemUserService;

    public UserServiceApiImpl(ISystemUserService systemUserService) {
        this.systemUserService = systemUserService;
    }

    @Override
    public User findByCode(String userCode) {
        return systemUserService.findByCode(userCode);
    }

    @Override
    public User findByMobile(String mobile) {
        return systemUserService.findByMobile(mobile);
    }

    @Override
    public User findByWeChatOpenID(String openid) {
        return null;
    }

    @Override
    public User findByWeChatUnionID(String unionid) {
        return null;
    }
}
