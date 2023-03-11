package com.zssedu.dao;

import com.github.pagehelper.Page;
import com.zssedu.base.dao.BaseDao;
import com.zssedu.entity.UserFollow;
import com.zssedu.vo.UserFollowVo;
import org.apache.ibatis.annotations.Param;

public interface UserFollowDao extends BaseDao<UserFollow> {
    Integer countByUserIdAndHouserId(@Param("userId")Long userId, @Param("houseId")Long houseId);

    Page<UserFollowVo> findListPage(@Param("userId")Long userId);
}
