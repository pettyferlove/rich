package com.github.rich.base.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.rich.base.entity.SystemMenuResource;
import com.github.rich.base.domain.vo.MenuNode;
import com.github.rich.security.userdetails.RichUserDetails;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Petty
 * @since 2019-05-21
 */
public interface ISystemMenuResourceService extends IService<SystemMenuResource> {

    /**
     * 创建节点
     *
     * @param userId userId
     * @param menu   前端传递的Menu node信息
     * @return 节点的Id
     */
    String createNode(String userId, SystemMenuResource menu);

    /**
     * 加载菜单树
     *
     * @return 集合
     */
    List<MenuNode> loadTree();

    /**
     * 加载用户得菜单
     *
     * @param userDetails 用户验证实体
     * @return 集合
     */
    List<MenuNode> loadMenu(RichUserDetails userDetails);

    /**
     * 通过Id获取节点信息
     *
     * @param id 菜单节点Id
     * @return MenuNode
     */
    MenuNode getNode(String id);

    /**
     * 通过Id删除节点信息
     * 当存在子节点的时候禁止删除
     *
     * @param id 菜单节点Id
     * @return Boolean
     * @throws Exception 存在子节点的时候抛出异常
     */
    Boolean deleteNode(String id) throws Exception;

    /**
     * 更新节点信息
     *
     * @param userId userId
     * @param menu   菜单节点信息实体
     * @return Boolean
     */
    Boolean updateNode(String userId, SystemMenuResource menu);

    /**
     * 根据父节点获取子树
     *
     * @param parentId 父节点Id
     * @return 集合
     */
    List<MenuNode> loadChildrenNodes(String parentId);

    /**
     * 更绝UserID查询拥有的资源标识
     *
     * @param userId userId
     * @return 集合
     */
    List<SystemMenuResource> loadPermissionsByUserId(String userId);
}
