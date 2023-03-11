package com.zssedu.service;

import com.zssedu.entity.Dict;

import java.util.List;
import java.util.Map;

public interface DictService {

    List<Map<String, Object>> findZnodes(Long id);

    /**
     * 根据上级id获取子节点数据列表
     * 区域下面的板块
     * @param parentId
     * @return
     */
    List<Dict> findListByParentId(Long parentId);

    /**
     * 根据编码获取子节点数据列表
     * 编码beijing对应北京下面的区域
     * @param dictCode
     * @return
     */
    List<Dict> findListByDictCode(String dictCode);


    String getNameById(Long id);
}
