package com.lym.service;

import com.lym.dto.ImageHolder;
import com.lym.dto.ProductExecution;
import com.lym.entity.Product;
import com.lym.exception.ProductOperationException;

import java.util.List;

/**
 * @ClassName 商品Service
 * @Description TODO
 * @Author lyming
 * @Date 2019/1/23 6:35 PM
 **/
public interface ProductService {

    /**
     * 查询商品列表并分页,可输入的条件有:商品名(模糊),商品状态,店铺id,商品类别
     * @param productCondition
     * @param pageIndex
     * @param pageSize
     * @return
     */
    ProductExecution getProductList(Product productCondition, int pageIndex, int pageSize);

    /**
     * 添加商品信息
     *
     * @param imageHolder     缩略图封装类(一张)
     * @param imageHolderList 详情图封装类集合(多张)
     * @return ProductExecution
     * @throws ProductOperationException
     */
    ProductExecution addProduct(Product product, ImageHolder imageHolder,
                                List<ImageHolder> imageHolderList) throws ProductOperationException;


    /**
     * 根据productId获取商品信息
     *
     * @param productId
     * @return
     */
    Product getProductById(Long productId);

    /**
     * 更新店铺信息
     *
     * @return
     * @throws ProductOperationException
     */
    ProductExecution modifyProduct(Product product, ImageHolder imageHolder, List<ImageHolder> imageHolderList)
            throws ProductOperationException;
}
