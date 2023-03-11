package com.zssedu.service;

import com.zssedu.base.service.BaseService;
import com.zssedu.entity.HouseBroker;

import java.util.List;

public interface HouseBrokerService extends BaseService<HouseBroker> {
    List<HouseBroker> findListById(Long houseId);
}
