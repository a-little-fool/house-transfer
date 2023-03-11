package com.zssedu.service;

import com.github.pagehelper.PageInfo;
import com.zssedu.base.service.BaseService;
import com.zssedu.entity.House;
import com.zssedu.vo.HouseQueryVo;
import com.zssedu.vo.HouseVo;

public interface HouseService extends BaseService<House> {

    void publish(Long id, Integer status);

    /**
     * 前台展示分页数据
     * @param pageNum
     * @param pageSize
     * @param houseQueryVo
     * @return
     */
    PageInfo<HouseVo> findListPage(int pageNum, int pageSize, HouseQueryVo houseQueryVo);
}
