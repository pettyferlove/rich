package com.github.rich.base.feign.impl;

import com.github.rich.base.dto.User;
import com.github.rich.base.dto.UserDetailDTO;
import com.github.rich.base.entity.SystemUser;
import com.github.rich.base.feign.UserServiceFeignClient;
import com.github.rich.base.service.ISystemUserService;
import com.github.rich.common.core.utils.ConverterUtil;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * @author Petty
 */
@RestController
public class UserServiceFeignClientImpl implements UserServiceFeignClient {

    private final ISystemUserService systemUserService;

    public UserServiceFeignClientImpl(ISystemUserService systemUserService) {
        this.systemUserService = systemUserService;
    }

    @Override
    public User findUserByLoginName(String loginName) {
        return systemUserService.findByLoginName(loginName);
    }

    @Override
    public User findUserByMobile(String mobile) {
        return systemUserService.findByMobile(mobile);
    }

    @Override
    public User findByWeChatOpenId(String openid) {
        return systemUserService.findByWeChatOpenId(openid);
    }

    @Override
    public User findByWeChatUnionId(String unionid) {
        return systemUserService.findByWeChatUnionId(unionid);
    }


    @Override
    public UserDetailDTO getUserDetail(String userId) {
        SystemUser systemUser = systemUserService.get(userId);
        Optional<UserDetailDTO> userDetailDTOOptional = Optional.ofNullable(ConverterUtil.convert(systemUser, new UserDetailDTO()));
        return userDetailDTOOptional.orElseGet(UserDetailDTO::new);
    }
}
