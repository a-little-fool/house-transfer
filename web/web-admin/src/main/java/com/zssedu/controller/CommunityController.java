package com.zssedu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.zssedu.base.controller.BaseController;
import com.zssedu.entity.Community;
import com.zssedu.entity.Dict;
import com.zssedu.service.CommunityService;
import com.zssedu.service.DictService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
 * @date 2023年03月04 21:06
 */
@Controller
@RequestMapping(value = "/community")
public class CommunityController extends BaseController {
    @Reference
    private CommunityService communityService;
    @Reference
    private DictService dictService;

    private final static String PAGE_INDEX = "community/index";
    private final static String PAGE_CREATE = "community/create";
    private final static String PAGE_EDIT = "community/edit";
    private final static String PAGE_SHOW = "community/show";
    private final static String PAGE_SUCCESS = "common/successPage";
    private final static String LIST_ACTION = "redirect:/community";

    /**
     * 小区列表展示
     * @param modelMap
     * @param request
     * @return
     */
    @RequestMapping
    @PreAuthorize("hasAuthority('community.show')")
    public String index(ModelMap modelMap, HttpServletRequest request) {
        Map<String,Object> filters = getFilters(request);
        PageInfo<Community> page = communityService.findPage(filters);
        List<Dict> areaList = dictService.findListByDictCode("beijing");
        modelMap.addAttribute("areaList", areaList);
        modelMap.addAttribute("page", page);
        modelMap.addAttribute("filters", filters);
        return PAGE_INDEX;
    }

    /**
     * 新增页面
     * @param modelMap
     * @return
     */
    @GetMapping("/create")
    @PreAuthorize("hasAuthority('community.create')")
    public String create(ModelMap modelMap) {
        List<Dict> areaList = dictService.findListByDictCode("beijing");
        modelMap.addAttribute("areaList", areaList);
        return PAGE_CREATE;
    }

    /**
     * 保存社区
     * @param community
     * @return
     */
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('community.create')")
    public String save(Community community) {
        communityService.insert(community);
        return PAGE_SUCCESS;
    }

    /**
     * 编辑页面
     * @param modelMap
     * @param id
     * @return
     */
    @GetMapping("/edit/{id}")
    @PreAuthorize("hasAuthority('community.edit')")
    public String edit(ModelMap modelMap, @PathVariable("id") Long id) {
        Community community = communityService.getById(id);
        List<Dict> areaList = dictService.findListByDictCode("beijing");
        modelMap.addAttribute("community", community);
        modelMap.addAttribute("areaList", areaList);
        return PAGE_EDIT;
    }

    /**
     * 更改社区信息
     * @param community
     * @return
     */
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('community.edit')")
    public String update(Community community) {
        communityService.update(community);
        return PAGE_SUCCESS;
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('community.delete')")
    public String delete(@PathVariable("id") Long id) {
        communityService.delete(id);
        return LIST_ACTION;
    }
}
