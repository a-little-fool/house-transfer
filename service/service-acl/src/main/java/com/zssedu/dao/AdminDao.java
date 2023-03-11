package com.zssedu.dao;

import com.github.pagehelper.Page;
import com.zssedu.base.dao.BaseDao;
import com.zssedu.entity.Admin;

import java.util.List;
import java.util.Map;

public interface AdminDao extends BaseDao<Admin> {
    Page<Admin> findPage(Map<String,Object> filters);

    List<Admin> findAll();


    Admin getByUsername(String username);
}
