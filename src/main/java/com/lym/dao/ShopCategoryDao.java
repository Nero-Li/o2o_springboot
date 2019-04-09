package com.lym.dao;

import com.lym.entity.ShopCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 店铺类别
 */
public interface ShopCategoryDao {

    /**
     * 查询店铺类别列表
     *
     * @param shopCategoryCondition
     * @return
     */
    List<ShopCategory> queryShopCategory(@Param("shopCategoryCondition") ShopCategory shopCategoryCondition);
}
