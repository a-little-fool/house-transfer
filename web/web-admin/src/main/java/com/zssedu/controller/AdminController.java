package com.zssedu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.zssedu.base.controller.BaseController;
import com.zssedu.entity.Admin;
import com.zssedu.service.AdminService;
import com.zssedu.service.RoleService;
import com.zssedu.util.QiniuUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

/**
 * @author 周书胜
 * @date 2023年03月03 9:27
 */
@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController {
    @Reference
    private AdminService adminService;

    @Reference
    private RoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final static String PAGE_INDEX = "admin/index";
    private final static String PAGE_CREATE = "admin/create";
    private final static String PAGE_EDIT = "admin/edit";
    private final static String PAGE_SUCCESS = "common/successPage";
    private final static String PAGE_REDIRECT_INDEX = "redirect:/admin";
    private final static String PAGE_UPLOED_SHOW = "admin/upload";
    private final static String PAGE_ASSIGN_SHOW = "admin/assignShow";

    /**
     * 用户管理页面主页
     * 需要调用service层查询admin表中的分页信息
     * @return
     */
    @RequestMapping
    @PreAuthorize("hasAuthority('admin.show')")
    public String index(ModelMap modelMap, HttpServletRequest request) {
        Map<String, Object> filters = getFilters(request);
        // 调用service层返回PageInfo对象
        PageInfo<Admin> page = adminService.findPage(filters);
        modelMap.addAttribute("filters", filters);
        modelMap.addAttribute("page", page);
        return PAGE_INDEX;
    }

    /**
     * 跳转新增页面
     * @return
     */
    @RequestMapping("/create")
    @PreAuthorize("hasAuthority('admin.create')")
    public String create() {
        return PAGE_CREATE;
    }

    /**
     * 新增页面
     * @return
     */
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('admin.save')")
    public String save(Admin admin, HttpServletRequest request) {
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        //设置默认头像
        admin.setHeadUrl("http://47.93.148.192:8080/group1/M00/03/F0/rBHu8mHqbpSAU0jVAAAgiJmKg0o148.jpg");
        adminService.insert(admin);

        return PAGE_SUCCESS;
    }

    /**
     * 跳转编辑页面，并进行数据恢复
     * @param id
     * @param modelMap
     * @return
     */
    @RequestMapping("edit/{id}")
    @PreAuthorize("hasAuthority('admin.edit')")
    public String edit(@PathVariable("id") Long id, ModelMap modelMap) {
        Admin admin = adminService.getById(id);
        modelMap.addAttribute("admin", admin);
        return PAGE_EDIT;
    }

    /**
     * 修改用户信息
     * @param admin
     * @return
     */
    @RequestMapping("/update")
    @PreAuthorize("hasAuthority('admin.edit')")
    public String update(Admin admin) {
        adminService.update(admin);
        return PAGE_SUCCESS;
    }

    /**
     * 删除用户信息
     * @param id
     * @return
     */
    @RequestMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('admin.delete')")
    public String delete(@PathVariable("id") Long id) {
        adminService.delete(id);
        return PAGE_REDIRECT_INDEX;
    }

    @GetMapping("/uploadShow/{id}")
    @PreAuthorize("hasAuthority('admin.show')")
    public String uploadShow(ModelMap model,@PathVariable Long id) {
        model.addAttribute("id", id);
        return PAGE_UPLOED_SHOW;
    }

    @PostMapping("/upload/{id}")
    @PreAuthorize("hasAuthority('admin.show')")
    public String upload(@PathVariable Long id, @RequestParam(value = "file") MultipartFile file, HttpServletRequest request) throws IOException {
        String newFileName =  UUID.randomUUID().toString() ;
        // 上传图片
        QiniuUtil.upload2Qiniu(file.getBytes(),newFileName);
        String url = "D:\\imageSource\\" + file.getOriginalFilename();
        Admin admin = new Admin();
        admin.setId(id);
//        admin.setHeadUrl("http://rr1ghdi3l.hn-bkt.clouddn.com/"+ newFileName);
        admin.setHeadUrl(url);
        System.out.println("adminUrl=" + url);
        adminService.update(admin);
        return PAGE_SUCCESS;
    }

    /**
     * 跳转assignShow.html页面
     * 未用户分配角色
     * @param adminId
     * @return
     */
    @GetMapping("/assignShow/{adminId}")
    @PreAuthorize("hasAuthority('admin.assign')")
    public String assignShow(@PathVariable("adminId") Long adminId, ModelMap modelMap) {
        modelMap.addAttribute("adminId", adminId);
        // map中包含了noAssginRoleList和assginRoleList
        Map<String, Object> roleMap = roleService.findRoleByAdminId(adminId);
        return PAGE_ASSIGN_SHOW;
    }

    /**
     * 根据用户分配角色
     * @param adminId
     * @param roleIds
     * @return
     */
    @PostMapping("/assignRole")
    @PreAuthorize("hasAuthority('admin.assign')")
    public String assignRole(Long adminId, Long[] roleIds) {
        roleService.saveUserRoleRealtionShip(adminId,roleIds);
        return PAGE_SUCCESS;
    }
}
