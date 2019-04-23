package com.github.rich.base.service;

import com.github.rich.base.model.User;
import com.github.rich.base.entity.SystemUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户信息 服务类
 * </p>
 *
 * @author Petty
 * @since 2019-04-23
 */
public interface ISystemUserService extends IService<SystemUser> {

    /**
     * 根据登录名查找用户信息
     * @param userCode 登录名
     * @return 用户信息
     */
    User findByCode(String userCode);

    /**
     * 根据手机号码查找用户信息
     * @param mobile 手机号码
     * @return 用户信息
     */
    User findByMobile(String mobile);

    /**
     * 根据微信OpenId查找用户信息
     * @param openid 微信开放授权ID
     * @return 用户信息
     */
    User findByWeChat(String openid);
}
