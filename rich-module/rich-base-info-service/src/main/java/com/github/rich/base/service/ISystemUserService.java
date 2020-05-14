package com.github.rich.base.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.rich.base.domain.dto.User;
import com.github.rich.base.entity.SystemUser;
import com.github.rich.base.domain.vo.ChangeMobileVO;
import com.github.rich.base.domain.vo.ChangePasswordVO;
import com.github.rich.base.domain.vo.UserDetailVO;
import com.github.rich.base.domain.vo.UserInfoVO;
import com.github.rich.security.userdetails.RichUserDetails;

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
     *
     * @param loginName 登录名
     * @return 用户信息
     */
    User findByLoginName(String loginName);

    /**
     * 根据手机号码查找用户信息
     *
     * @param mobile 手机号码
     * @return 用户信息
     */
    User findByMobile(String mobile);

    /**
     * 根据微信OpenId查找用户信息
     *
     * @param openid 微信开放授权ID
     * @return 用户信息
     */
    User findByWeChatOpenId(String openid);

    /**
     * 根据微信UnionId查找用户信息
     *
     * @param unionid 微信开放授权ID
     * @return 用户信息
     */
    User findByWeChatUnionId(String unionid);

    /**
     * 通过OpenID 和UnionID 进行用户注册
     *
     * @param openid  微信OpenID
     * @param unionid 微信UnionID
     * @return Boolean
     */
    Boolean registerByWeChat(String openid, String unionid);


    /**
     * List查找
     *
     * @param user 查询参数对象
     * @param page Page分页对象
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
     * @param userId userId
     * @param user   要创建的对象
     * @return Boolean
     */
    String create(String userId, SystemUser user);

    /**
     * 更新数据（必须带Code）
     *
     * @param userId userId
     * @param user   对象
     * @return Boolean
     */
    Boolean update(String userId, SystemUser user);

    /**
     * 获取用户信息
     *
     * @param userDetails UserDetailsImpl
     * @return UserDetailVO
     */
    UserDetailVO getUserDetail(RichUserDetails userDetails);

    /**
     * 更新用户详情
     *
     * @param userDetails UserDetailsImpl
     * @param info        UserInfoVO
     * @return Boolean
     */
    Boolean updateUserInfo(RichUserDetails userDetails, UserInfoVO info);


    /**
     * 变更用户密码
     *
     * @param userDetails    UserDetailsImpl
     * @param changePassword 密码变更类
     * @return 1 变更成功 0 变更失败 2 两次输入的密码不一致 3 原始密码错误
     */
    Integer changePassword(RichUserDetails userDetails, ChangePasswordVO changePassword);


    /**
     * 变更用户手机号
     *
     * @param userDetails  UserDetailsImpl
     * @param changeMobile 手机号码变更类
     * @return 1 变更成功 0 变更失败 2 验证码错误
     */
    Integer changeMobile(RichUserDetails userDetails, ChangeMobileVO changeMobile);


    /**
     * 检查登录名是否存在
     *
     * @param loginName 登录名
     * @return 存在True 不存在False
     */
    Boolean checkLoginName(String loginName);

    /**
     * 检查手机号码是否存在
     *
     * @param mobile 手机号码
     * @return 存在True 不存在False
     */
    Boolean checkMobile(String mobile);

}
