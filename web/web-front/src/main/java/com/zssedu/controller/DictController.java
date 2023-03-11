package com.zssedu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.zssedu.entity.Dict;
import com.zssedu.result.Result;
import com.zssedu.service.DictService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 周书胜
 * @date 2023年03月07 10:45
 */
@RestController
@RequestMapping("/dict")
public class DictController {
    @Reference
    private DictService dictService;

    /**
     * 根据编码获取子节点数据列表
     * @param dictCode
     * @return
     */
    @GetMapping("/findListByDictCode/{dictCode}")
    public Result findListByDictCode(@PathVariable("dictCode") String dictCode) {
        List<Dict> listByDictCode = dictService.findListByDictCode(dictCode);
        return Result.ok(listByDictCode);
    }

    /**
     * 根据上级id获取子节点数据列表
     * @param parentId
     * @return
     */
    @GetMapping(value = "findListByParentId/{parentId}")
    public Result<List<Dict>> findListByParentId(@PathVariable Long parentId) {
        List<Dict> list = dictService.findListByParentId(parentId);
        return Result.ok(list);
    }
}
