package com.zssedu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.zssedu.entity.HouseUser;
import com.zssedu.service.HouseUserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

/**
 * @author 周书胜
 * @date 2023年03月05 18:17
 */
@Controller
@RequestMapping("/houseUser")
public class HouseUserController {
    @Reference
    private HouseUserService houseUserService;

    private final static String PAGE_CREATE = "houseUser/create";
    private final static String PAGE_SUCCESS = "common/successPage";
    private final static String PAGE_EDIT = "houseUser/edit";
    private final static String LIST_ACTION = "redirect:/house/";

    /**
     * 进入新增
     * @param model
     * @return
     */
    @GetMapping("/create")
    @PreAuthorize("hasAuthority('house.editUser')")
    public String create(ModelMap model,@RequestParam("houseId") Long houseId) {
        model.addAttribute("houseId",houseId);
        return PAGE_CREATE;
    }

    /**
     * 保存新增
     * @param houseUser
     * @return
     */
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('house.editUser')")
    public String save(HouseUser houseUser) {
        houseUserService.insert(houseUser);
        return PAGE_SUCCESS;
    }

    /**
     * 编辑
     * @param model
     * @param id
     * @return
     */
    @GetMapping("/edit/{id}")
    @PreAuthorize("hasAuthority('house.editUser')")
    public String edit(ModelMap model,@PathVariable Long id) {
        HouseUser houseUser = houseUserService.getById(id);
        model.addAttribute("houseUser",houseUser);
        return PAGE_EDIT;
    }

    /**
     * 保存更新
     * @param houseUser
     * @return
     */
    @PostMapping(value="/update")
    @PreAuthorize("hasAuthority('house.editUser')")
    public String update(HouseUser houseUser) {
        houseUserService.update(houseUser);
        return PAGE_SUCCESS;
    }

    /**
     * 删除
     * @param model
     * @param id
     * @return
     */
    @GetMapping("/delete/{houseId}/{id}")
    @PreAuthorize("hasAuthority('house.editUser')")
    public String delete(ModelMap model, @PathVariable Long houseId, @PathVariable Long id) {
        houseUserService.delete(id);
        return LIST_ACTION + houseId;
    }
}
