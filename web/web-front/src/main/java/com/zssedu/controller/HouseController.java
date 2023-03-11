package com.zssedu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.zssedu.entity.*;
import com.zssedu.result.Result;
import com.zssedu.service.*;
import com.zssedu.vo.HouseQueryVo;
import com.zssedu.vo.HouseVo;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 周书胜
 * @date 2023年03月07 14:40
 */
@RestController
@RequestMapping("/house")
public class HouseController {
    @Reference
    private HouseService houseService;

    @Reference
    private CommunityService communityService;

    @Reference
    private DictService dictService;

    @Reference
    private HouseImageService houseImageService;

    @Reference
    private HouseBrokerService houseBrokerService;

    @Reference
    private UserFollowService userFollowService;

    /**
     * 房源列表
     *
     * @return
     */
    @PostMapping(value = "/list/{pageNum}/{pageSize}")
    public Result findListPage(@RequestBody HouseQueryVo houseQueryVo,
                               @PathVariable Integer pageNum,
                               @PathVariable Integer pageSize) {
        PageInfo<HouseVo> pageInfo = houseService.findListPage(pageNum, pageSize, houseQueryVo);
        return Result.ok(pageInfo);
    }

    /**
     * 房源详情页面
     * @param id
     * @return
     */
    @GetMapping("/info/{id}")
    public Result info(@PathVariable Long id, HttpServletRequest request) {
        House house = houseService.getById(id);
        Community community = communityService.getById(house.getCommunityId());
        List<HouseBroker> houseBrokerList = houseBrokerService.findListById(id);
        List<HouseImage> houseImage1List = houseImageService.findById(id, 1);

        Map<String, Object> map = new HashMap<>();
        map.put("house",house);
        map.put("community",community);
        map.put("houseBrokerList",houseBrokerList);
        map.put("houseImage1List",houseImage1List);

        UserInfo userInfo = (UserInfo)request.getSession().getAttribute("USER");
        Boolean isFollow = false;
        if(null != userInfo) {
            Long userId = userInfo.getId();
            isFollow = userFollowService.isFollowed(userId, id);
        }
        map.put("isFollow",isFollow);
        return Result.ok(map);
    }
}
