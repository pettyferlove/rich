package com.github.rich.base.api.impl;

import com.github.rich.base.api.UserServiceApi;
import com.github.rich.base.dto.User;
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
    public User getByLoginCode(String loginCode) {
        return systemUserService.findByLoginCode(loginCode);
    }

    @Override
    public User getByMobile(String mobile) {
        return systemUserService.findByMobile(mobile);
    }

    @Override
    public User getByWeChatOpenID(String openid) {
        return systemUserService.findByWeChatOpenID(openid);
    }

    @Override
    public User getByWeChatUnionID(String unionid) {
        return systemUserService.findByWeChatUnionID(unionid);
    }
}
