package com.zssedu.base.service;

import com.github.pagehelper.PageInfo;

import java.io.Serializable;
import java.util.Map;

public interface BaseService<T> {
    int insert(T t);

    void delete(Serializable id);

    T getById(Serializable id);

    int update(T t);

    PageInfo<T> findPage(Map<String, Object> filters);
}
