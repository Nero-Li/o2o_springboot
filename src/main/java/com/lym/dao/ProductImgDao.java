package com.lym.dao;

import com.lym.entity.ProductImg;

import java.util.List;

public interface ProductImgDao {

    /**
     * 批量添加商品图片(商品详情图片)
     *
     * @param productImgList
     * @return
     */
    int batchInsertProductImg(List<ProductImg> productImgList);

    /**
     * 根据商品id删除商品下所有详情图
     *
     * @param productId
     * @return
     */
    int deleteProductImgByProductId(long productId);

    /**
     * 根据ProductId查询某个商品下所有详情图
     *
     * @param productId
     * @return
     */
    List<ProductImg> queryProductImgList(Long productId);
}
