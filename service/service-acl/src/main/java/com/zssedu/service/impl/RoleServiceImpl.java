package com.zssedu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.zssedu.base.dao.BaseDao;
import com.zssedu.base.service.impl.BaseServiceImpl;
import com.zssedu.dao.AdminRoleDao;
import com.zssedu.dao.RoleDao;
import com.zssedu.entity.AdminRole;
import com.zssedu.entity.Role;
import com.zssedu.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 周书胜
 * @date 2023年03月01 11:44
 */
@Service(interfaceClass = RoleService.class)
@Transactional
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService {
    @Autowired
    private RoleDao roleDao;

    // 多对多抽取出来的表
    @Autowired
    private AdminRoleDao adminRoleDao;

    @Override
    protected BaseDao<Role> getEntityDao() {
        return roleDao;
    }

    public List<Role> findAll() {
        return roleDao.findAll();
    }

    @Override
    public Map<String, Object> findRoleByAdminId(Long adminId) {
        // 先查询所有的角色
        List<Role> allRolesList = roleDao.findAll();
        // 拥有的角色id
        List<Long> exitRoleIdList = adminRoleDao.findRoleIdByAdminId(adminId);
        // 对角色进行分类
        List<Role> noAssginRoleList = new ArrayList<>();
        List<Role> assginRoleList = new ArrayList<>();
        for (Role role : allRolesList) {
            if (exitRoleIdList.contains(role.getId())) {
                assginRoleList.add(role);
            } else {
                noAssginRoleList.add(role);
            }
        }

        // 封装map返回数据
        Map<String,Object> roleMap = new HashMap<>();
        roleMap.put("noAssginRoleList", noAssginRoleList);
        roleMap.put("assginRoleList", assginRoleList);
        return roleMap;
    }

    /**
     * 分配角色
     * @param adminId
     * @param roleIds
     */
    @Override
    public void saveUserRoleRealtionShip(Long adminId, Long[] roleIds) {
        adminRoleDao.deleteByAdminId(adminId);

        for(Long roleId : roleIds) {
            if(StringUtils.isEmpty(roleId)) continue;
            AdminRole userRole = new AdminRole();
            userRole.setAdminId(adminId);
            userRole.setRoleId(roleId);
            adminRoleDao.insert(userRole);
        }
    }
}
