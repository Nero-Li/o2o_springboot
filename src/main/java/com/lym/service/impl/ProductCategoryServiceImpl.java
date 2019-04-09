package com.lym.service.impl;

import com.lym.dao.ProductCategoryDao;
import com.lym.dao.ProductDao;
import com.lym.dto.ProductCategoryExecution;
import com.lym.entity.ProductCategory;
import com.lym.enums.ProductCategoryStateEnum;
import com.lym.exception.ProductCategoryOperationException;
import com.lym.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService{

	@Autowired
	private ProductCategoryDao productCategoryDao;

    @Autowired
    private ProductDao productDao;
	
	@Override
	public List<ProductCategory> getProductCategoryList(Long shopId) {
		
		return productCategoryDao.queryProductCategoryList(shopId);
	}

    @Override
    @Transactional
    public ProductCategoryExecution batchAddProductCategory(List<ProductCategory> productCategoryList) throws ProductCategoryOperationException {
        if (productCategoryList != null && productCategoryList.size() > 0) {
            try {
                int effectNum = productCategoryDao.batchInsertProductCategory(productCategoryList);
                if (effectNum <= 0) {
                    throw new ProductCategoryOperationException("店铺创建失败");
                } else {
                    return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS);
                }
            } catch (Exception e) {
                throw new ProductCategoryOperationException("batchAddProductCategory error:" + e.getMessage());
            }
        } else {
            return new ProductCategoryExecution(ProductCategoryStateEnum.EMPTY_LIST);
        }
    }

    @Override
    @Transactional
    public ProductCategoryExecution deleteProductCategory(long productCategoryId, long shopId) throws ProductCategoryOperationException {
        /**
         * 解除tb_product里的商品与该productCategoryId的关联
         */
        try {
            int effectNum = productDao.updateProductCategoryToNull(productCategoryId);
            if (effectNum < 0) {
                throw new RuntimeException("商品类别更新失败!");
            }
        } catch (Exception e) {
            throw new RuntimeException("delete ProductCategory error:" + e.getMessage());
        }
        /**
         * 删除该productCategory
         */
        try {
            int effectNum = productCategoryDao.deleteProductCategory(productCategoryId, shopId);
            if (effectNum < 0) {
                throw new ProductCategoryOperationException("删除商品类别失败!");
            } else {
                return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS);
            }
        } catch (Exception e) {
            throw new ProductCategoryOperationException("delete ProductCategory error:" + e.getMessage());
        }
    }

}
