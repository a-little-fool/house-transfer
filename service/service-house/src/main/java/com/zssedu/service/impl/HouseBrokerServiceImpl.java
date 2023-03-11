package com.zssedu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.zssedu.base.dao.BaseDao;
import com.zssedu.base.service.impl.BaseServiceImpl;
import com.zssedu.dao.HouseBrokerDao;
import com.zssedu.entity.HouseBroker;
import com.zssedu.service.HouseBrokerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 周书胜
 * @date 2023年03月05 16:29
 */
@Service(interfaceClass = HouseBrokerService.class)
@Transactional
public class HouseBrokerServiceImpl extends BaseServiceImpl<HouseBroker> implements HouseBrokerService {
    @Autowired
    private HouseBrokerDao houseBrokerDao;

    @Override
    protected BaseDao<HouseBroker> getEntityDao() {
        return houseBrokerDao;
    }

    @Override
    public List<HouseBroker> findListById(Long id) {
        return houseBrokerDao.findListByHouseId(id);
    }
}
