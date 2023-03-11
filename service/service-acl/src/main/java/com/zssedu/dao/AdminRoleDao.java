package com.zssedu.dao;

import com.zssedu.base.dao.BaseDao;
import com.zssedu.entity.AdminRole;

import java.util.List;

public interface AdminRoleDao extends BaseDao<AdminRole> {
    // 根据用户id获取已选的角色id
    List<Long> findRoleIdByAdminId(Long adminId);

    void deleteByAdminId(Long adminId);
}
