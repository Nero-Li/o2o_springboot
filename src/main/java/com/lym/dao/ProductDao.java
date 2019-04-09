package com.lym.dao;

import com.lym.entity.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductDao {

    /**
     * 查询商品列表并分页,可输入的条件有:商品名(模糊),商品状态,店铺id,店铺类别
     *
     * @param productCondition 查询条件
     * @param beginIndex
     * @param pageSize
     * @return
     */
    List<Product> queryProductList(@Param("productCondition") Product productCondition, @Param("rowIndex") int beginIndex,
                                   @Param("pageSize") int pageSize);

    /**
     * 查询条件对应的商品总数
     *
     * @param productCondition
     * @return
     */
    int queryProductCount(@Param("productCondition") Product productCondition);

    /**
     * 插入商品
     **/
    int insertProduct(Product product);

    /**
     * 根据productId查询商品信息
     *
     * @param productId
     * @return
     */
    Product getProductById(Long productId);

    /**
     * 根据productId删除商品信息
     *
     * @param productId
     * @return
     */
    int deleteProductById(Long productId);

    /**
     * 更新店铺信息
     *
     * @param product
     * @return
     */
    int updateProduct(Product product);

    /**
     * 删除商品类别之前,先将商品类别id置为null
     * @param productCategoryId
     * @return 影响的行数
     */
    int updateProductCategoryToNull(long productCategoryId);
}
