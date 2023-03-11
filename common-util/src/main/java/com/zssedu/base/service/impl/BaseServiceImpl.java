package com.zssedu.base.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zssedu.base.dao.BaseDao;
import com.zssedu.base.service.BaseService;
import com.zssedu.util.CastUtil;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Map;

/**
 * @author 周书胜
 * @date 2023年03月03 8:36
 */
@Transactional
public abstract class BaseServiceImpl<T> implements BaseService<T> {

    protected abstract BaseDao<T> getEntityDao();

    public int insert(T t) {
        return getEntityDao().insert(t);
    }

    public void delete(Serializable id) {
        getEntityDao().delete(id);
    }

    public int update(T t) {
        return getEntityDao().update(t);
    }

    public T getById(Serializable id) {
        return getEntityDao().getById(id);
    }

    public PageInfo<T> findPage(Map<String, Object> filters) {
        //当前页数
        int pageNum = CastUtil.castInt(filters.get("pageNum"), 1);
        //每页显示的记录条数
        int pageSize = CastUtil.castInt(filters.get("pageSize"), 10);

        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<T>(getEntityDao().findPage(filters), 10);
    }
}
