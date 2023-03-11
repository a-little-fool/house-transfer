package com.zssedu.config;

import com.alibaba.dubbo.config.annotation.Reference;
import com.zssedu.entity.Admin;
import com.zssedu.service.AdminService;
import com.zssedu.service.PermissionService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author 周书胜
 * @date 2023年03月11 11:02
 */
@Component
public class UserDetailsServiceImpl implements UserDetailsService {
    @Reference
    private AdminService adminService;

    @Reference
    private PermissionService permissionService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("username=" + username);
        Admin admin = adminService.getByUsername(username);
        if (null == admin) {
            throw new UsernameNotFoundException("用户名不存在!");
        }

        // 用户功能权限
        List<String> codeList = permissionService.findCodeListByAdminId(admin.getId());
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        for (String code : codeList) {
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(code);
            authorities.add(simpleGrantedAuthority);
        }

        return new User(username, admin.getPassword(), authorities);
    }
}
