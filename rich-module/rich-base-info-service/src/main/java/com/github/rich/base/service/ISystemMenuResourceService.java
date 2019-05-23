package com.github.rich.base.service;

import com.github.rich.base.entity.SystemMenuResource;
import com.baomidou.mybatisplus.extension.service.IService;

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
     * @return Boolean
     */
    Boolean createNode(SystemMenuResource menu);
}
