package com.zssedu.dao;

import com.zssedu.base.dao.BaseDao;
import com.zssedu.entity.RolePermission;

import java.util.List;

public interface RolePermissionDao extends BaseDao<RolePermission> {
    void deleteByRoleId(Long roleId);

    List<Long> findPermissionIdListByRoleId(Long roleId);
}
