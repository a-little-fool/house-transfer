package com.zssedu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.zssedu.base.dao.BaseDao;
import com.zssedu.base.service.impl.BaseServiceImpl;
import com.zssedu.dao.AdminDao;
import com.zssedu.entity.Admin;
import com.zssedu.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 周书胜
 * @date 2023年03月03 9:27
 */
@Service(interfaceClass = AdminService.class)
@Transactional
public class AdminServiceImpl extends BaseServiceImpl<Admin> implements AdminService {
    @Autowired
    private AdminDao adminDao;

    @Override
    protected BaseDao<Admin> getEntityDao() {
        return adminDao;
    }

    @Override
    public List<Admin> findAll() {
        return adminDao.findAll();
    }

    @Override
    public Admin getByUsername(String username) {
        return adminDao.getByUsername(username);
    }
}
