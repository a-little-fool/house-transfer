package com.zssedu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.zssedu.base.controller.BaseController;
import com.zssedu.entity.*;
import com.zssedu.service.*;
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
 * @date 2023年03月05 10:26
 */
@Controller
@RequestMapping("/house")
public class HouseController extends BaseController {
    @Reference
    private HouseService houseService;

    @Reference
    private CommunityService communityService;

    @Reference
    private DictService dictService;

    @Reference
    private HouseImageService houseImageService;

    @Reference
    private HouseUserService houseUserService;

    @Reference
    private HouseBrokerService houseBrokerService;

    private final static String PAGE_INDEX = "house/index";
    private final static String PAGE_SUCCESS = "common/successPage";
    private final static String PAGE_CREATE = "house/create";
    private final static String PAGE_EDIT = "house/edit";
    private final static String PAGE_SHOW = "house/show";
    private final static String LIST_ACTION = "redirect:/house";

    /**
     * 房源管理主页
     * 1. 分页显示
     * 2. 返回房子的属性，小区【要根据这些来筛选】
     * @param modelMap
     * @param request
     * @return
     */
    @RequestMapping
    @PreAuthorize("hasAuthority('house.show')")
    public String index(ModelMap modelMap, HttpServletRequest request) {
        Map<String, Object> filters = getFilters(request);
        // 分页查询
        PageInfo<House> page = houseService.findPage(filters);
        // 获取小区
        List<Community> communityList = communityService.findAll();
        modelMap.addAttribute("page", page);
        modelMap.addAttribute("filters", filters);
        init(modelMap);
        return PAGE_INDEX;
    }

    @GetMapping("/create")
    @PreAuthorize("hasAuthority('house.create')")
    public String create(ModelMap modelMap) {
        init(modelMap);
        return PAGE_CREATE;
    }

    @PostMapping("/save")
    @PreAuthorize("hasAuthority('house.create')")
    public String save(House house) {
        houseService.insert(house);
        return PAGE_SUCCESS;
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("hasAuthority('house.edit')")
    public String edit(@PathVariable("id") Long id, ModelMap modelMap) {
        // 根据id查询house，返回到edit.html
        House house = houseService.getById(id);
        modelMap.addAttribute("house", house);
        init(modelMap);
        return PAGE_EDIT;
    }

    @PostMapping(value="/update")
    @PreAuthorize("hasAuthority('house.edit')")
    public String update(House house) {
        houseService.update(house);
        return PAGE_SUCCESS;
    }

    @GetMapping("delete/{id}")
    @PreAuthorize("hasAuthority('house.delete')")
    public String delete(@PathVariable("id") Long id) {
        houseService.delete(id);
        return LIST_ACTION;
    }

    /**
     * 更改发布信息
     * @param id
     * @param status
     * @return
     */
    @GetMapping("publish/{id}/{status}")
    @PreAuthorize("hasAuthority('house.publish')")
    public String publish(@PathVariable("id") Long id, @PathVariable("status") Integer status) {
        houseService.publish(id, status);
        return LIST_ACTION;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('house.show')")
    public String show(@PathVariable("id") Long id, ModelMap modelMap) {
        House house = houseService.getById(id);
        Community community = communityService.getById(house.getCommunityId());
        List<HouseUser> houseUserList = houseUserService.findListById(id);
        List<HouseBroker> houseBrokerList = houseBrokerService.findListById(id);
        List<HouseImage> houseImage1List = houseImageService.findById(id, 1);
        List<HouseImage> houseImage2List = houseImageService.findById(id, 2);
        modelMap.addAttribute("house", house);
        modelMap.addAttribute("community", community);
        modelMap.addAttribute("houseUserList", houseUserList);
        modelMap.addAttribute("houseBrokerList", houseBrokerList);
        modelMap.addAttribute("houseImage1List", houseImage1List);
        modelMap.addAttribute("houseImage2List", houseImage2List);
        return PAGE_SHOW;
    }


    /**
     * 抽取方法
     */
    public void init(ModelMap modelMap) {
        // 获取小区
        List<Community> communityList = communityService.findAll();
        // 获取属性
        List<Dict> houseTypeList = dictService.findListByDictCode("houseType");
        List<Dict> floorList = dictService.findListByDictCode("floor");
        List<Dict> buildStructureList = dictService.findListByDictCode("buildStructure");
        List<Dict> directionList = dictService.findListByDictCode("direction");
        List<Dict> decorationList = dictService.findListByDictCode("decoration");
        List<Dict> houseUseList = dictService.findListByDictCode("houseUse");
        // 给前台返回数据
        modelMap.addAttribute("communityList", communityList);
        modelMap.addAttribute("houseTypeList", houseTypeList);
        modelMap.addAttribute("floorList", floorList);
        modelMap.addAttribute("buildStructureList", buildStructureList);
        modelMap.addAttribute("directionList", directionList);
        modelMap.addAttribute("decorationList", decorationList);
        modelMap.addAttribute("houseUseList", houseUseList);
    }


}
