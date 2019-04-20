package com.github.rich.demo.controller;

import com.github.rich.base.dto.User;
import com.github.rich.base.feign.RemoteRoleService;
import com.github.rich.base.feign.RemoteUserService;
import com.github.rich.common.core.model.R;
import com.github.rich.security.annotation.InnerServiceSecurity;
import com.github.rich.security.service.impl.UserDetailsImpl;
import com.github.rich.security.utils.SecurityUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/demo")
public class DemoController {

    private final RemoteRoleService remoteRoleService;
    private final RemoteUserService remoteUserService;

    public DemoController(RemoteRoleService remoteRoleService, RemoteUserService remoteUserService) {
        this.remoteRoleService = remoteRoleService;
        this.remoteUserService = remoteUserService;
    }

    @GetMapping("/demo")
    @InnerServiceSecurity
    public void demo(){
        UserDetailsImpl user = SecurityUtil.getUser();
        System.out.println(user);
        System.out.println("Success");
        R<List> admin = remoteRoleService.find();
        System.out.println(admin);
    }
}
