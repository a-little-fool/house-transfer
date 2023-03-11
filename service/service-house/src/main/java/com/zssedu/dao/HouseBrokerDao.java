package com.zssedu.dao;

import com.zssedu.base.dao.BaseDao;
import com.zssedu.entity.HouseBroker;

import java.util.List;

public interface HouseBrokerDao extends BaseDao<HouseBroker> {
    List<HouseBroker> findListByHouseId(Long houseId);
}
