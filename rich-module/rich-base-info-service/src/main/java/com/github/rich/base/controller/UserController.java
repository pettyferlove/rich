package com.github.rich.base.controller;

import com.github.rich.base.dto.User;
import com.github.rich.base.service.ISystemUserService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

    @PostMapping("/find/code/{userCode}")
    public User findByCode(@PathVariable String userCode) {
        return systemUserService.findByCode(userCode);
    }
}
