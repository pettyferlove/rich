package com.github.rich.base.service.impl;

import com.github.rich.base.dto.User;
import com.github.rich.base.entity.SystemUser;
import com.github.rich.base.mapper.SystemUserMapper;
import com.github.rich.base.service.ISystemUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.rich.common.core.utils.ConverterUtil;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户信息 服务实现类
 * </p>
 *
 * @author Petty
 * @since 2019-04-18
 */
@Service
public class SystemUserServiceImpl extends ServiceImpl<SystemUserMapper, SystemUser> implements ISystemUserService {

    /**
     * 根据登录名查找用户信息
     *
     * @param userCode 登录名
     * @return 用户信息
     */
    @Override
    public User findByCode(String userCode) {
        User user = new User();
        SystemUser systemUser = this.getById(userCode);
        ConverterUtil.convert(systemUser ,user);
        return user;
    }

    /**
     * 根据手机号码查找用户信息
     *
     * @param mobile 手机号码
     * @return 用户信息
     */
    @Override
    public User findByMobile(String mobile) {
        return null;
    }

    /**
     * 根据微信OpenId查找用户信息
     *
     * @param openid 微信开放授权ID
     * @return 用户信息
     */
    @Override
    public User findByWeChat(String openid) {
        return null;
    }
}
