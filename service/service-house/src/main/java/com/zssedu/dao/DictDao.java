package com.zssedu.dao;

import com.zssedu.base.dao.BaseDao;
import com.zssedu.entity.Dict;

import java.util.List;

public interface DictDao extends BaseDao<Dict> {
    List<Dict> findListByParentId(Long id);

    Integer countIsParent(Long id);

    Dict getByDictCode(String dictCode);

    String getNameById(Long id);
}
