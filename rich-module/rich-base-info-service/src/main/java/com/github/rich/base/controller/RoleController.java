package com.github.rich.base.controller;

import com.github.rich.base.service.ISystemRoleService;
import com.github.rich.common.core.model.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Petty
 */
@RestController
@RequestMapping("/role")
public class RoleController {
    private final ISystemRoleService systemRoleService;

    public RoleController(ISystemRoleService systemRoleService) {
        this.systemRoleService = systemRoleService;
    }

    @GetMapping("/find")
    public R<List> find(){
        return new R<>(systemRoleService.list());
    }
}
