package com.zssedu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.zssedu.entity.Dict;
import com.zssedu.result.Result;
import com.zssedu.service.DictService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author 周书胜
 * @date 2023年03月03 17:33
 */
@Controller
@RequestMapping("/dict")
public class DictController {
    @Reference
    private DictService dictService;

    private final static String PAGE_INDEX = "dict/index";

    @GetMapping
    public String index() {
        return PAGE_INDEX;
    }

    /**
     * 根据父id获取其子结点
     * @param id
     * @return
     */
    @GetMapping("/findZnodes")
    @ResponseBody
    public Result findZnodes(@RequestParam(value = "id", defaultValue = "0") Long id) {
        List<Map<String,Object>> zNodes = dictService.findZnodes(id);
        return Result.ok(zNodes);
    }

    @GetMapping(value = "/findListByParentId/{parentId}")
    @ResponseBody
    public Result<List<Dict>> findListByParentId(@PathVariable("parentId") Long parentId) {
        List<Dict> list =
                dictService.findListByParentId(parentId);
        return Result.ok(list);
    }

    @GetMapping(value = "/findListByDictCode/{dictCode}")
    @ResponseBody
    public Result<List<Dict>> findListByDictCode(@PathVariable("dictCode") String dictCode) {
        List<Dict> list =
                dictService.findListByDictCode(dictCode);
        return Result.ok(list);
    }


}
