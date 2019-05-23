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
}
