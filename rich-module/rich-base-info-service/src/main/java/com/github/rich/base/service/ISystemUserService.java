package com.github.rich.base.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.rich.base.dto.User;
import com.github.rich.base.entity.SystemUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.rich.base.vo.UserDetailVO;
import com.github.rich.base.vo.UserInfoVO;
import com.github.rich.security.service.impl.UserDetailsImpl;
import com.github.rich.security.utils.SecurityUtil;

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
     * @param loginName 登录名
     * @return 用户信息
     */
    User findByLoginName(String loginName);

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
    User findByWeChatOpenID(String openid);

    /**
     * 根据微信UnionId查找用户信息
     * @param unionid 微信开放授权ID
     * @return 用户信息
     */
    User findByWeChatUnionID(String unionid);

    /**
     * 通过OpenID 和UnionID 进行用户注册
     * @param openid 微信OpenID
     * @param unionid 微信UnionID
     * @return Boolean
     */
    Boolean registerByWeChatOpenID(String openid,String unionid);


    /**
     * List查找
     *
     * @param user 查询参数对象
     * @param page     Page分页对象
     * @return IPage 返回结果
     */
    IPage<SystemUser> page(SystemUser user, Page<SystemUser> page);

    /**
     * 通过Id查询Role信息
     *
     * @param id 业务主键
     * @return 对象
     */
    SystemUser get(String id);

    /**
     * 通过Id删除信息
     *
     * @param id 业务主键
     * @return Boolean
     */
    Boolean delete(String id);

    /**
     * 创建数据
     *
     * @param user 要创建的对象
     * @return Boolean
     */
    String create(SystemUser user);

    /**
     * 更新数据（必须带Code）
     *
     * @param user 对象
     * @return Boolean
     */
    Boolean update(SystemUser user);

    /**
     * 获取用户信息
     * @param userDetails UserDetailsImpl
     * @return UserInfoVO
     */
    UserInfoVO getUserInfo(UserDetailsImpl userDetails);

    /**
     * 更新用户详情
     * @param userDetails UserDetailsImpl
     * @param detail UserDetailVO
     * @return Boolean
     */
    Boolean updateUserInfo(UserDetailsImpl userDetails,UserDetailVO detail);

}
