package com.zssedu.service;

import com.zssedu.base.service.BaseService;
import com.zssedu.entity.Admin;

import java.util.List;

public interface AdminService extends BaseService<Admin> {

    List<Admin> findAll();

    Admin getByUsername(String username);
}
