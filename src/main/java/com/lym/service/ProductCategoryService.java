package com.lym.service;

import com.lym.dto.ProductCategoryExecution;
import com.lym.entity.ProductCategory;
import com.lym.exception.ProductCategoryOperationException;

import java.util.List;

/**
 * 商品类别Service
 * @author Administrator
 *
 */
public interface ProductCategoryService {

	/**
	 * 查询指定店铺下所有商品类别信息
	 * @param shopId
	 * @return
	 */
	List<ProductCategory> getProductCategoryList(Long shopId);


    /**
     * 批量增加商品类别
     *
     * @param productCategoryList
     * @return
     */
    ProductCategoryExecution batchAddProductCategory(List<ProductCategory> productCategoryList) throws ProductCategoryOperationException;

    /**
     * 删除商品类别(将此类别下的商品里的类别id置为null,再删除该商品类别)
     *
     * @param productCategoryId
     * @param shopId
     * @return
     * @throws ProductCategoryOperationException
     */
    ProductCategoryExecution deleteProductCategory(long productCategoryId, long shopId) throws ProductCategoryOperationException;

}
