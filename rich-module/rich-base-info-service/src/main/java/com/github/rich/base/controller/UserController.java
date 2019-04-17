package com.github.rich.base.controller;

import com.github.rich.base.entity.SystemUser;
import com.github.rich.base.service.ISystemUserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Petty
 */
@RestController
@RequestMapping("/user")
public class UserController {
    private final ISystemUserService systemUserService;

    public UserController(ISystemUserService systemUserService) {
        this.systemUserService = systemUserService;
    }

    @RequestMapping("/detail")
    public SystemUser detail(String userCode){
        return systemUserService.getById(userCode);
    }
}
