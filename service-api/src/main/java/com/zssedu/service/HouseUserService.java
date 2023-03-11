package com.zssedu.service;

import com.zssedu.base.service.BaseService;
import com.zssedu.entity.HouseUser;

import java.util.List;

public interface HouseUserService extends BaseService<HouseUser> {
    List<HouseUser> findListById(Long houseId);
}
