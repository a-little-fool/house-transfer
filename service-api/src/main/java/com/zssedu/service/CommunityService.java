package com.zssedu.service;

import com.zssedu.base.service.BaseService;
import com.zssedu.entity.Community;

import java.util.List;

public interface CommunityService extends BaseService<Community> {
    List<Community> findAll();

}
