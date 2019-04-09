package com.lym.dao;

import com.lym.entity.ProductCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 店铺商品类别Dao
 * @author Administrator
 *
 */
public interface ProductCategoryDao {

	/**
	 * 通过shopId查询店铺商品类别
	 * @param shopId
	 * @return
	 */
	List<ProductCategory> queryProductCategoryList(Long shopId);


    /**
     * 批量新增商品类别
     *
     * @param productCategoryList
     * @return
     */
    int batchInsertProductCategory(List<ProductCategory> productCategoryList);


    /**
     * 删除指定商品类别
     *
     * @param productCategoryId
     * @param shopId
     * @return
     */
    int deleteProductCategory(@Param("productCategoryId") long productCategoryId, @Param("shopId") long shopId);
}
