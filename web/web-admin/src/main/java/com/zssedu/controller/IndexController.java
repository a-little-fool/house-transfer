package com.zssedu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.zssedu.entity.Admin;
import com.zssedu.entity.Permission;
import com.zssedu.service.AdminService;
import com.zssedu.service.PermissionService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author 周书胜
 * @date 2023年03月01 13:28
 */
@Controller
public class IndexController {
    private final static String PAGE_INDEX = "frame/index";
    private final static String PAGE_MAIN = "frame/main";
    private final static String PAGE_LOGIN = "frame/login";
    private final static String PAGE_AUTH = "frame/auth";

    @Reference
    private AdminService adminService;

    @Reference
    private PermissionService permissionService;

    // 去首页
//    @RequestMapping("/")
//    public String toIndexPage() {
//        return PAGE_INDEX;
//    }

    /**
     * 框架首页
     *
     * @return
     */
    @GetMapping("/")
    public String index(ModelMap model) {
        //后续替换为当前登录用户id
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        Admin admin = adminService.getByUsername(user.getUsername());
        List<Permission> permissionList = permissionService.findMenuPermissionByAdminId(admin.getId());
        model.addAttribute("admin", admin);
        model.addAttribute("permissionList", permissionList);
        return PAGE_INDEX;
    }

    // 去main页面
    @RequestMapping("/main")
    public String toMainPage() {
        return PAGE_MAIN;
    }

    @RequestMapping("/login")
    public String toLoginPage() {
        return PAGE_LOGIN;
    }

    @RequestMapping("/auth")
    public String auth() {
        return PAGE_AUTH;
    }
}
