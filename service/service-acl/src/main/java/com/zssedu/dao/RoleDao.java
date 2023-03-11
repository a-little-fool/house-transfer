package com.zssedu.dao;

import com.zssedu.base.dao.BaseDao;
import com.zssedu.entity.Role;

import java.util.List;

public interface RoleDao extends BaseDao<Role> {
    List<Role> findAll();
}
