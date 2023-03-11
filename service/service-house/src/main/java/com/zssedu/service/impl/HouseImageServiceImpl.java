package com.zssedu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.zssedu.base.dao.BaseDao;
import com.zssedu.base.service.impl.BaseServiceImpl;
import com.zssedu.dao.HouseImageDao;
import com.zssedu.entity.HouseImage;
import com.zssedu.service.HouseImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 周书胜
 * @date 2023年03月05 16:29
 */
@Service(interfaceClass = HouseImageService.class)
@Transactional
public class HouseImageServiceImpl extends BaseServiceImpl<HouseImage> implements HouseImageService {
    @Autowired
    private HouseImageDao houseImageDao;

    @Override
    protected BaseDao<HouseImage> getEntityDao() {
        return houseImageDao;
    }


    @Override
    public List<HouseImage> findById(Long houseId, Integer type) {
        return houseImageDao.findList(houseId, type);
    }
}
