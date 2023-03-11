package com.zssedu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.zssedu.base.controller.BaseController;
import com.zssedu.entity.UserInfo;
import com.zssedu.result.Result;
import com.zssedu.service.UserFollowService;
import com.zssedu.vo.UserFollowVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author 周书胜
 * @date 2023年03月07 20:37
 */
@RestController
@RequestMapping("/userFollow")
public class UserFollowController extends BaseController {
    @Reference
    private UserFollowService userFollowService;

    /**
     * 关注房源
     * @param houseId
     * @param request
     * @return
     */
    @GetMapping("/auth/follow/{houseId}")
    public Result follow(@PathVariable("houseId") Long houseId, HttpServletRequest request) {
        UserInfo userInfo = (UserInfo) request.getSession().getAttribute("USER");
        Long userId = userInfo.getId();
        userFollowService.follow(userId, houseId);
        return Result.ok();
    }

    @GetMapping(value = "/auth/list/{pageNum}/{pageSize}")
    public Result findListPage(@PathVariable Integer pageNum,
                               @PathVariable Integer pageSize,
                               HttpServletRequest request) {
        UserInfo userInfo = (UserInfo)request.getSession().getAttribute("USER");
        Long userId = userInfo.getId();
        PageInfo<UserFollowVo> pageInfo = userFollowService.findListPage(pageNum, pageSize, userId);
        return Result.ok(pageInfo);
    }

    @GetMapping("/auth/cancelFollow/{id}")
    public Result cancelFollow(@PathVariable("id") Long id, HttpServletRequest request){
        userFollowService.cancelFollow(id);
        return Result.ok();
    }
}
