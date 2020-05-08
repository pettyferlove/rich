package com.github.rich.demo.controller;

import com.github.rich.base.feign.GatewayRouteServiceFeignClient;
import com.github.rich.security.annotation.InnerServiceSecurity;
import com.github.rich.security.service.impl.UserDetailsImpl;
import com.github.rich.security.utils.SecurityUtil;
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

    private final GatewayRouteServiceFeignClient gatewayRouteServiceFeignClient;

    public DemoController(GatewayRouteServiceFeignClient gatewayRouteServiceFeignClient) {
        this.gatewayRouteServiceFeignClient = gatewayRouteServiceFeignClient;
    }

    @GetMapping("/demo")
    @InnerServiceSecurity
    public void demo(){
        UserDetailsImpl user = SecurityUtil.getUser();
        List<String> roles = SecurityUtil.getRoles();
        System.out.println(user);
        System.out.println(roles);
        System.out.println(gatewayRouteServiceFeignClient.loadRoutes());
    }
}
