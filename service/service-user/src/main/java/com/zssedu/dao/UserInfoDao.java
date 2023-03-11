package com.zssedu.dao;

import com.zssedu.base.dao.BaseDao;
import com.zssedu.entity.UserInfo;

public interface UserInfoDao extends BaseDao<UserInfo> {
    UserInfo getByPhone(String phone);
}
