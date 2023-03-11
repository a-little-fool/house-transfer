package com.zssedu.service;

import com.zssedu.base.service.BaseService;
import com.zssedu.entity.Role;

import java.util.List;
import java.util.Map;

public interface RoleService extends BaseService<Role> {
    List<Role> findAll();

    /**
     * 根据用户获取角色数据：包括已选角色和未选角色
     * @param adminId
     * @return
     */
    Map<String, Object> findRoleByAdminId(Long adminId);

    /**
     * 分配角色
     * @param adminId
     * @param roleIds
     */
    void saveUserRoleRealtionShip(Long adminId, Long[] roleIds);
}
