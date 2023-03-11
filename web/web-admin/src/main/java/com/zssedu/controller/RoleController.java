package com.zssedu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.zssedu.base.controller.BaseController;
import com.zssedu.entity.Role;
import com.zssedu.service.PermissionService;
import com.zssedu.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author 周书胜
 * @date 2023年03月01 11:47
 */
@Controller
@RequestMapping("/role")
public class RoleController extends BaseController {
    private final static String PAGE_SUCCESS = "common/successPage";
    private final static String PAGE_INDEX = "role/index";
    private final static String PAGE_ASSIGN_SHOW = "role/assignShow";

    @Reference
    private RoleService roleService;

    @Reference
    private PermissionService permissionService;

    // 显示所有角色信息
//    @RequestMapping
//    public ModelAndView index() {
//        List<Role> roleList = roleService.findAll();
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("role/index");
//        modelAndView.addObject("list", roleList);
//        return modelAndView;
//    }

    // 去添加角色信息页面
    @RequestMapping("/create")
    @PreAuthorize("hasAuthority('role.create')")
    public String create() {
        return "role/create";
    }

    // 新增角色信息——create的真正逻辑
    @RequestMapping("/save")
    @PreAuthorize("hasAuthority('role.create')")
    public String save(Role role) {
        // 调用service层添加角色信息
        roleService.insert(role);
        // 下面的方式不友好
//        return "redirect:/role";
        // 我们让其加载父页面，优化
        return PAGE_SUCCESS;
    }

    // 删除角色信息
    @RequestMapping("/delete/{roleId}")
    @PreAuthorize("hasAuthority('role.delete')")
    public String delete(@PathVariable("roleId") Long roleId) {
        // 调用service层删除角色
        roleService.delete(roleId);
        // 重定向到权限管理页面，这里没有弹框，不用去successPage.html页面
        return "redirect:/role";
    }

    // 修改角色信息
    @RequestMapping("edit/{roleId}")
    @PreAuthorize("hasAuthority('role.edit')")
    public String edit(@PathVariable("roleId") Long roleId, ModelMap modelMap) {
        // 调用service层获取角色信息
        Role role = roleService.getById(roleId);
        // 跳转edit页面，恢复数据
        modelMap.addAttribute("role", role);
        return "role/edit";
    }

    // 修改角色信息
    @RequestMapping("update")
    @PreAuthorize("hasAuthority('role.edit')")
    public String update(Role role) {
        roleService.update(role);
        return PAGE_SUCCESS;
    }

    // 分页显示角色信息
    @RequestMapping
    @PreAuthorize("hasAuthority('role.show')")
    public String index(ModelMap modelMap, HttpServletRequest request) {
        Map<String, Object> filters = getFilters(request);
        modelMap.addAttribute("filters", filters);
        // 调用roleService，获取当前分页记录
        PageInfo<Role> page = roleService.findPage(filters);
        modelMap.addAttribute("page", page);
        return PAGE_INDEX;
    }

    /**
     * 进入分配权限页面
     * @param roleId
     * @return
     */
    @GetMapping("/assignShow/{roleId}")
    @PreAuthorize("hasAuthority('role.assign')")
    public String assignShow(ModelMap model,@PathVariable Long roleId) {
        List<Map<String,Object>> zNodes = permissionService.findPermissionByRoleId(roleId);
        model.addAttribute("zNodes", zNodes);
        model.addAttribute("roleId", roleId);
        return PAGE_ASSIGN_SHOW;
    }

    /**
     * 给角色分配权限
     * @param roleId
     * @param permissionIds
     * @return
     */
    @PostMapping("/assignPermission")
    @PreAuthorize("hasAuthority('role.assign')")
    public String assignPermission(Long roleId,Long[] permissionIds) {
        permissionService.saveRolePermissionRealtionShip(roleId, permissionIds);
        return PAGE_SUCCESS;
    }
}
