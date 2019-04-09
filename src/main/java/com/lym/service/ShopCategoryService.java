package com.lym.service;

import com.lym.entity.ShopCategory;

import java.util.List;

public interface ShopCategoryService {

    /**
     * redis的key前缀
     */
    public static String SCLISTKEY = "shopcategorylist";
	/**
	 * 根据查询条件获取店铺类型列表
	 * 
	 * @param shopCategoryCondition
	 * @return
	 */
	List<ShopCategory> getShopCategoryList(ShopCategory shopCategoryCondition);
}
