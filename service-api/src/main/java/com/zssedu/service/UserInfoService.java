package com.zssedu.service;

import com.zssedu.base.service.BaseService;
import com.zssedu.entity.UserInfo;

public interface UserInfoService extends BaseService<UserInfo> {
    UserInfo getByPhone(String phone);
}
