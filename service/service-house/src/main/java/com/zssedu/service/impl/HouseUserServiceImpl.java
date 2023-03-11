package com.zssedu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.zssedu.base.dao.BaseDao;
import com.zssedu.base.service.impl.BaseServiceImpl;
import com.zssedu.dao.HouseUserDao;
import com.zssedu.entity.HouseUser;
import com.zssedu.service.HouseBrokerService;
import com.zssedu.service.HouseImageService;
import com.zssedu.service.HouseUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 周书胜
 * @date 2023年03月05 16:29
 */
@Service(interfaceClass = HouseUserService.class)
@Transactional
public class HouseUserServiceImpl extends BaseServiceImpl<HouseUser> implements HouseUserService {
    @Autowired
    private HouseUserDao houseUserDao;

    @Override
    protected BaseDao<HouseUser> getEntityDao() {
        return houseUserDao;
    }

    @Override
    public List<HouseUser> findListById(Long id) {
        return houseUserDao.findListByHouseId(id);
    }
}
