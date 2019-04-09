package com.lym.dao;

import com.lym.entity.Area;

import java.util.List;

public interface AreaDao {
	
	/**
	  *  查询区域列表
	 * @return areaList
	 */
	List<Area> queryArea();
}
