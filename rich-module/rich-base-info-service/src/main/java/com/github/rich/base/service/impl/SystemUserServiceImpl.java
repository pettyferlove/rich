package com.github.rich.base.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
 * @since 2019-04-23
 */
@Service
public class SystemUserServiceImpl extends ServiceImpl<SystemUserMapper, SystemUser> implements ISystemUserService {

    @Override
    public User findByCode(String userCode) {
        User user = new User();
        SystemUser systemUser = this.getById(userCode);
        ConverterUtil.convert(systemUser, user);
        return user;
    }

    @Override
    public User findByMobile(String mobile) {
        User user = new User();
        SystemUser systemUser = this.getOne(new QueryWrapper<SystemUser>().eq("mobile_tel", mobile));
        ConverterUtil.convert(systemUser, user);
        return user;
    }

    @Override
    public User findByWeChatOpenID(String openid) {
        User user = new User();
        SystemUser systemUser = this.getOne(new QueryWrapper<SystemUser>().eq("wechat_openid", openid));
        ConverterUtil.convert(systemUser, user);
        return user;
    }

    @Override
    public User findByWeChatUnionID(String unionid) {
        User user = new User();
        SystemUser systemUser = this.getOne(new QueryWrapper<SystemUser>().eq("wechat_unionid", unionid));
        ConverterUtil.convert(systemUser, user);
        return user;
    }
}
