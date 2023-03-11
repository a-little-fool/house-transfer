package com.zssedu.service;

import com.zssedu.base.service.BaseService;
import com.zssedu.entity.HouseImage;

import java.util.List;

public interface HouseImageService extends BaseService<HouseImage> {
    List<HouseImage> findById(Long houseId, Integer type);
}
