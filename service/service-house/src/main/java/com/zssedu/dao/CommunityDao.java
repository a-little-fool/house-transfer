package com.zssedu.dao;

import com.zssedu.base.dao.BaseDao;
import com.zssedu.entity.Community;

import java.util.List;

public interface CommunityDao extends BaseDao<Community> {
    List<Community> findAll();
}
