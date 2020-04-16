package com.github.rich.base.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.rich.base.entity.SystemRoleMenu;
import com.github.rich.base.mapper.SystemRoleMenuMapper;
import com.github.rich.base.service.ISystemRoleMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 角色菜单关联 服务实现类
 * </p>
 *
 * @author Petty
 * @since 2019-05-21
 */
@Service
public class SystemRoleMenuServiceImpl extends ServiceImpl<SystemRoleMenuMapper, SystemRoleMenu> implements ISystemRoleMenuService {

    @Override
    public void addBatch(String roleId, List<String> addIdList) {
        List<SystemRoleMenu> systemRoleMenus = new ArrayList<>();
        for (String menuId : addIdList) {
            SystemRoleMenu systemRoleMenu = new SystemRoleMenu();
            systemRoleMenu.setRoleId(roleId);
            systemRoleMenu.setMenuId(menuId);
            systemRoleMenus.add(systemRoleMenu);
        }
        if (systemRoleMenus.size() != 0) {
            this.saveBatch(systemRoleMenus);
        }
    }

    @Override
    public void removeBatch(String roleId, List<String> removeIdList) {
        if (removeIdList.size() > 0) {
            List<SystemRoleMenu> systemRoleMenus = this.list(Wrappers.<SystemRoleMenu>lambdaQuery().eq(SystemRoleMenu::getRoleId,roleId).in(SystemRoleMenu::getMenuId, removeIdList));
            List<String> ids = new ArrayList<>();
            for (SystemRoleMenu systemRoleMenu : systemRoleMenus) {
                ids.add(systemRoleMenu.getId());
            }
            if (ids.size() > 0) {
                this.removeByIds(ids);
            }
        }
    }
}
