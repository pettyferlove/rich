package com.github.rich.base.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.rich.base.entity.SystemMenuResource;
import com.github.rich.base.vo.MenuNode;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Petty
 * @since 2019-05-21
 */
public interface ISystemMenuResourceService extends IService<SystemMenuResource> {

    /**
     * 创建节点
     * @param menu 前端传递的Menu node信息
     * @return 节点的Code
     */
    String createNode(SystemMenuResource menu);

    /**
     * 加载菜单树
     * @return 集合
     */
    List<MenuNode> loadTree();

    /**
     * 通过Code获取节点信息
     * @param code 菜单节点Code
     * @return MenuNode
     */
    MenuNode getNode(String code);

    /**
     * 通过Code删除节点信息
     * 当存在子节点的时候禁止删除
     * @param code 菜单节点Code
     * @return Boolean
     * @throws  Exception 存在子节点的时候抛出异常
     */
    Boolean deleteNode(String code) throws Exception;

    /**
     * 更新节点信息
     * @param menu 菜单节点信息实体
     * @return Boolean
     */
    Boolean updateNode(SystemMenuResource menu);

    /**
     * 根据父节点获取子树
     * @param parentCode 父节点Code
     * @return 集合
     */
    List<MenuNode> loadChildrenNodes(String parentCode);
}
