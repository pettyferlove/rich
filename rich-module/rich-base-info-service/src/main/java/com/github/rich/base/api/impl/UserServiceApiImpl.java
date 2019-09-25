package com.github.rich.base.api.impl;

import com.github.rich.base.api.UserServiceApi;
import com.github.rich.base.dto.User;
import com.github.rich.base.dto.UserDetailDTO;
import com.github.rich.base.entity.SystemUser;
import com.github.rich.base.service.ISystemUserService;
import com.github.rich.common.core.utils.ConverterUtil;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

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
    public User getByLoginName(String loginName) {
        return systemUserService.findByLoginName(loginName);
    }

    @Override
    public User getByMobile(String mobile) {
        return systemUserService.findByMobile(mobile);
    }

    @Override
    public User getByWeChatOpenId(String openid) {
        return systemUserService.findByWeChatOpenId(openid);
    }

    @Override
    public User getByWeChatUnionId(String unionid) {
        return systemUserService.findByWeChatUnionId(unionid);
    }

    @Override
    public UserDetailDTO getUserDetail(String userId) {
        SystemUser systemUser = systemUserService.get(userId);
        Optional<UserDetailDTO> userDetailDTOOptional = Optional.ofNullable(ConverterUtil.convert(systemUser,new UserDetailDTO()));
        return userDetailDTOOptional.orElseGet(UserDetailDTO::new);
    }
}
