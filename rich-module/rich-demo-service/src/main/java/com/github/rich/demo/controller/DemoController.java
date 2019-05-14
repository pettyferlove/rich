package com.github.rich.demo.controller;

import com.github.rich.base.feign.RemoteGatewayRouteService;
import com.github.rich.base.feign.RemoteUserService;
import com.github.rich.security.annotation.InnerServiceSecurity;
import com.github.rich.security.service.impl.UserDetailsImpl;
import com.github.rich.security.utils.SecurityUtil;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * @author Petty
 */
@RestController
@RequestMapping("/demo")
public class DemoController {

    private final RemoteGatewayRouteService remoteGatewayRouteService;

    public DemoController(RemoteGatewayRouteService remoteGatewayRouteService) {
        this.remoteGatewayRouteService = remoteGatewayRouteService;
    }

    @GetMapping("/demo")
    @InnerServiceSecurity
    public void demo(){
        UserDetailsImpl user = SecurityUtil.getUser();
        List<String> roles = SecurityUtil.getRoles();
        System.out.println(user);
        System.out.println(roles);
        System.out.println(remoteGatewayRouteService.loadRoutes());
    }
}
