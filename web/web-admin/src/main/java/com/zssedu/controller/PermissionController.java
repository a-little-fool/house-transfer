package com.zssedu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.zssedu.entity.Permission;
import com.zssedu.service.PermissionService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author 周书胜
 * @date 2023年03月11 8:35
 */
@Controller
@RequestMapping("/permission")
public class PermissionController {
    @Reference
    private PermissionService permissionService;

    private final static String LIST_ACTION = "redirect:/permission";

    private final static String PAGE_INDEX = "permission/index";
    private final static String PAGE_CREATE = "permission/create";
    private final static String PAGE_EDIT = "permission/edit";
    private final static String PAGE_SUCCESS = "common/successPage";


    /**
     * 获取菜单
     * @return
     */
    @GetMapping
    @PreAuthorize("hasAuthority('permission.show')")
    public String index(ModelMap model) {
        List<Permission> list = permissionService.findAllMenu();
        model.addAttribute("list", list);
        return PAGE_INDEX;
    }

    /**
     * 进入新增
     * @param model
     * @param permission
     * @return
     */
    @GetMapping("/create")
    @PreAuthorize("hasAuthority('permission.create')")
    public String create(ModelMap model, Permission permission) {
        model.addAttribute("permission",permission);
        return PAGE_CREATE;
    }

    /**
     * 保存新增
     * @param permission
     * @return
     */
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('permission.create')")
    public String save(Permission permission) {

        permissionService.insert(permission);

        return PAGE_SUCCESS;
    }

    /**
     * 编辑
     * @param model
     * @param id
     * @return
     */
    @GetMapping("/edit/{id}")
    @PreAuthorize("hasAuthority('permission.edit')")
    public String edit(ModelMap model,@PathVariable Long id) {
        Permission permission = permissionService.getById(id);
        model.addAttribute("permission",permission);
        return PAGE_EDIT;
    }

    /**
     * 保存更新
     * @param permission
     * @return
     */
    @PostMapping(value="/update")
    @PreAuthorize("hasAuthority('permission.edit')")
    public String update(Permission permission) {
        permissionService.update(permission);
        return PAGE_SUCCESS;
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @GetMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('permission.delete')")
    public String delete(@PathVariable Long id) {
        permissionService.delete(id);
        return LIST_ACTION;
    }
}
