package com.zssedu.dao;

import com.github.pagehelper.Page;
import com.zssedu.base.dao.BaseDao;
import com.zssedu.entity.House;
import com.zssedu.vo.HouseQueryVo;
import com.zssedu.vo.HouseVo;
import org.apache.ibatis.annotations.Param;

public interface HouseDao extends BaseDao<House> {
    Page<HouseVo> findListPage(@Param("vo") HouseQueryVo houseQueryVo);
}
