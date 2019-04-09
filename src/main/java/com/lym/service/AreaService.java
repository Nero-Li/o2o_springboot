package com.lym.service;

import com.lym.entity.Area;

import java.util.List;

/**
  * 区域相关Service层
 * @author lyming
 *
 */
public interface AreaService {

    /**
     * redis的key前缀
     */
    public static String AREALISYKEY = "arealist";

	/**
	  * 查询区域列表
	 * @return areaList
	 */
	List<Area> getAreaList();
}
