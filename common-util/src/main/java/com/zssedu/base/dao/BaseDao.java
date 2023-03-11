package com.zssedu.base.dao;

import com.github.pagehelper.Page;

import java.io.Serializable;
import java.util.Map;

public interface BaseDao<T> {
    int insert(T t);

    void delete(Serializable id);

    T getById(Serializable id);

    int update(T t);

    Page<T> findPage(Map<String, Object> filters);
}
