package com.zssedu.dao;

import com.zssedu.base.dao.BaseDao;
import com.zssedu.entity.HouseUser;

import java.util.List;

public interface HouseUserDao extends BaseDao<HouseUser> {
    List<HouseUser> findListByHouseId(Long houseId);
}
