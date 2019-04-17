package com.github.rich.auth.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 管理员-用户管理
 * @author Petty
 */
@Controller
@RequestMapping("admin")
public class AdminController {

    @GetMapping("user/list")
    public String index() {
        return "/admin/user/list";
    }

    @GetMapping("user/create")
    public String create() {
        return "/admin/user/create";
    }

    @GetMapping("user/group")
    public String group() {
        return "/admin/user/group";
    }

    @GetMapping("oauth2/client")
    public String oauth2client() {
        return "admin/oauth2/client/index";
    }

    @GetMapping("oauth2/scope")
    public String oauth2scope() {
        return "admin/oauth2/scope/index";
    }
}
