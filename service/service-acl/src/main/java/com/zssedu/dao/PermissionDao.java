package com.zssedu.dao;

import com.zssedu.base.dao.BaseDao;
import com.zssedu.entity.Permission;

import java.util.List;

public interface PermissionDao extends BaseDao<Permission> {
    List<Permission> findAll();

    List<Permission> findListByAdminId(Long adminId);

    List<String> findAllCodeList();


    List<String> findCodeListByAdminId(Long adminId);
}
