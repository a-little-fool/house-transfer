package com.zssedu.service;

import com.github.pagehelper.PageInfo;
import com.zssedu.base.service.BaseService;
import com.zssedu.entity.UserFollow;
import com.zssedu.vo.UserFollowVo;

public interface UserFollowService extends BaseService<UserFollow> {
    /**
     * 关注房源
     * @param userId
     * @param houseId
     */
    void follow(Long userId, Long houseId);

    /**
     * 用户是否关注房源
     * @param userId
     * @param houseId
     * @return
     */
    Boolean isFollowed(Long userId, Long houseId);

    PageInfo<UserFollowVo> findListPage(int pageNum, int pageSize, Long userId);

    /**
     * 取消关注
     * @param id
     * @return
     */
    void cancelFollow(Long id);
}
