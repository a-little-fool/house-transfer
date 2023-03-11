package com.zssedu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.zssedu.entity.Admin;
import com.zssedu.entity.House;
import com.zssedu.entity.HouseBroker;
import com.zssedu.service.AdminService;
import com.zssedu.service.HouseBrokerService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 周书胜
 * @date 2023年03月05 17:16
 */
@Controller
@RequestMapping("/houseBroker")
public class HouseBrokerController {
    @Reference
    private HouseBrokerService houseBrokerService;

    @Reference
    private AdminService adminService;

    private final static String PAGE_CREATE = "houseBroker/create";
    private final static String PAGE_SUCCESS = "common/successPage";
    private final static String PAGE_EDIT = "houseBroker/edit";
    private final static String LIST_ACTION = "redirect:/house/";

    /**
     * 跳转到create.html页面
     * @param houseId
     * @return
     */
    @RequestMapping("/create")
    @PreAuthorize("hasAuthority('house.editBroker')")
    public String create(@RequestParam("houseId") Long houseId, ModelMap modelMap) {
        // 给houseId和adminList
        List<Admin> adminList = adminService.findAll();
        modelMap.addAttribute("houseId", houseId);
        modelMap.addAttribute("adminList", adminList);
        return PAGE_CREATE;
    }

    /**
     * 保存新增
     * @param houseBroker
     * @return
     */
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('house.editBroker')")
    public String save(HouseBroker houseBroker) {
        Admin admin = adminService.getById(houseBroker.getBrokerId());
        houseBroker.setBrokerName(admin.getName());
        houseBroker.setBrokerHeadUrl(admin.getHeadUrl());
        houseBrokerService.insert(houseBroker);
        return PAGE_SUCCESS;
    }

    /**
     * 编辑
     * @param model
     * @param id
     * @return
     */
    @GetMapping("/edit/{id}")
    @PreAuthorize("hasAuthority('house.editBroker')")
    public String edit(ModelMap model,@PathVariable Long id) {
        HouseBroker houseBroker = houseBrokerService.getById(id);
        List<Admin> adminList = adminService.findAll();
        model.addAttribute("adminList",adminList);
        model.addAttribute("houseBroker",houseBroker);
        return PAGE_EDIT;
    }

    /**
     * 保存更新
     * @param houseBroker
     * @return
     */
    @PostMapping(value="/update")
    @PreAuthorize("hasAuthority('house.editBroker')")
    public String update(HouseBroker houseBroker) {
        Admin admin = adminService.getById(houseBroker.getBrokerId());
        houseBroker.setBrokerName(admin.getName());
        houseBroker.setBrokerHeadUrl(admin.getHeadUrl());
        houseBrokerService.update(houseBroker);

        return PAGE_SUCCESS;
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @GetMapping("/delete/{houseId}/{id}")
    @PreAuthorize("hasAuthority('house.editBroker')")
    public String delete(@PathVariable Long houseId, @PathVariable Long id) {
        houseBrokerService.delete(id);
        System.out.println("HouseBrokerController——delete method");
        return LIST_ACTION + houseId;
    }
}
