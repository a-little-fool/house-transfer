package com.zssedu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.zssedu.base.dao.BaseDao;
import com.zssedu.base.service.impl.BaseServiceImpl;
import com.zssedu.dao.DictDao;
import com.zssedu.entity.Dict;
import com.zssedu.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 周书胜
 * @date 2023年03月03 17:36
 */
@Service(interfaceClass = DictService.class)
@Transactional
public class DictServiceImpl extends BaseServiceImpl<Dict> implements DictService {
    @Autowired
    private DictDao dictDao;

    @Override
    protected BaseDao<Dict> getEntityDao() {
        return dictDao;
    }

    @Override
    public List<Map<String, Object>> findZnodes(Long id) {
        // 需要返回数据：[{ id:2, isParent:true, name:"随意勾选 2"}]
        // 获取子节点数据列表
        List<Dict> dictList = dictDao.findListByParentId(id);

        // 将子节点集合封装为List<Map<String,Object>>类型
        List<Map<String,Object>> zNodes = new ArrayList<>();
        for (Dict dict : dictList) {
            // 查询，判断该结点是否为父结点
            Integer count = dictDao.countIsParent(dict.getId());
            // 开始封装
            Map<String,Object> map = new HashMap<>();
            map.put("id", dict.getId());
            map.put("isParent", count > 0 ? true : false);
            map.put("name", dict.getName());
            zNodes.add(map);
        }

        return zNodes;
    }

    /**
     * 根据区域获取板块信息
     * @param parentId
     * @return
     */
    @Override
    public List<Dict> findListByParentId(Long parentId) {
        return dictDao.findListByParentId(parentId);
    }

    /**
     * 根据beijing获取区域
     * @param dictCode
     * @return
     */
    @Override
    public List<Dict> findListByDictCode(String dictCode) {
        Dict dict = dictDao.getByDictCode(dictCode);
        if (dict == null) return null; // 查不到该编码对应的城市
        return this.findListByParentId(dict.getId());
    }

    @Override
    public String getNameById(Long id) {
        return dictDao.getNameById(id);
    }
}
