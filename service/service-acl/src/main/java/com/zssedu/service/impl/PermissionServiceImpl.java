package com.zssedu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.zssedu.base.dao.BaseDao;
import com.zssedu.base.service.impl.BaseServiceImpl;
import com.zssedu.dao.PermissionDao;
import com.zssedu.dao.RolePermissionDao;
import com.zssedu.entity.Permission;
import com.zssedu.entity.RolePermission;
import com.zssedu.helper.PermissionHelper;
import com.zssedu.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 周书胜
 * @date 2023年03月08 19:49
 */
@Service(interfaceClass = PermissionService.class)
@Transactional
public class PermissionServiceImpl extends BaseServiceImpl<Permission> implements PermissionService {
    @Autowired
    private PermissionDao permissionDao;

    @Autowired
    private RolePermissionDao rolePermissionDao;

    @Override
    protected BaseDao<Permission> getEntityDao() {
        return permissionDao;
    }

    @Override
    public List<Map<String,Object>> findPermissionByRoleId(Long roleId) {
        //全部权限列表
        List<Permission> permissionList = permissionDao.findAll();

        //获取角色已分配的权限数据
        List<Long> permissionIdList = rolePermissionDao.findPermissionIdListByRoleId(roleId);

        //构建ztree数据
        //参考文档：http://www.treejs.cn/v3/demo.php#_201
        // { id:2, pId:0, name:"随意勾选 2", checked:true, open:true},
        List<Map<String,Object>> zNodes = new ArrayList<>();
        for(Permission permission : permissionList) {
            Map<String,Object> map = new HashMap<>();
            map.put("id", permission.getId());
            map.put("pId", permission.getParentId());
            map.put("name", permission.getName());
            if(permissionIdList.contains(permission.getId())) {
                map.put("checked", true);
            }
            zNodes.add(map);
        };
        return zNodes;
    }

    /**
     * 保存角色权限
     * @param roleId
     * @param permissionIds
     */
    @Override
    public void saveRolePermissionRealtionShip(Long roleId, Long[] permissionIds) {
        rolePermissionDao.deleteByRoleId(roleId);

        for(Long permissionId : permissionIds) {
            if(StringUtils.isEmpty(permissionId)) continue;
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(roleId);
            rolePermission.setPermissionId(permissionId);
            rolePermissionDao.insert(rolePermission);
        }
    }

    @Override
    public List<Permission> findAllMenu() {
        //全部权限列表
        List<Permission> permissionList = permissionDao.findAll();
        if(CollectionUtils.isEmpty(permissionList)) return null;

        //构建树形数据,总共三级
        //把权限数据构建成树形结构数据
        List<Permission> result = PermissionHelper.bulid(permissionList);
        return result;
    }

    public List<Permission> findMenuPermissionByAdminId(Long adminId) {
        List<Permission> permissionList = null;
        //admin账号id为：1
        if(adminId.longValue() == 1) {
            //如果是超级管理员，获取所有菜单
            permissionList = permissionDao.findAll();
        } else {
            permissionList = permissionDao.findListByAdminId(adminId);
        }
        //把权限数据构建成树形结构数据
        List<Permission> result = PermissionHelper.bulid(permissionList);
        return result;
    }

    @Override
    public List<String> findCodeListByAdminId(Long adminId) {
        //超级管理员admin账号id为：1
        if(adminId.longValue() == 1) {
            return permissionDao.findAllCodeList();
        }
        return permissionDao.findCodeListByAdminId(adminId);
    }
}
