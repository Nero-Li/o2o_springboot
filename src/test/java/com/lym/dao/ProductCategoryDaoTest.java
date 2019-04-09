package com.lym.dao;

import com.lym.entity.ProductCategory;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryDaoTest   {

    Logger logger = LoggerFactory.getLogger(ProductCategoryDaoTest.class);

    @Autowired
    private ProductCategoryDao productCategoryDao;

    @Test
    public void AtestProductCategoryList() {
        List<ProductCategory> list = productCategoryDao.queryProductCategoryList(1L);
        logger.debug("测试断点");
    }

    @Test
    public void BtestBatchInsertProductCategory() {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setProductCategoryName("商品类别1");
        productCategory.setPriority(1);
        productCategory.setCreateTime(new Date());
        productCategory.setShopId(1L);

        ProductCategory productCategory2 = new ProductCategory();
        productCategory2.setProductCategoryName("商品类别2");
        productCategory2.setPriority(2);
        productCategory2.setCreateTime(new Date());
        productCategory2.setShopId(1L);

        ProductCategory productCategory3 = new ProductCategory();
        productCategory3.setProductCategoryName("商品类别3");
        productCategory3.setPriority(3);
        productCategory3.setCreateTime(new Date());
        productCategory3.setShopId(1L);

        List<ProductCategory> productCategories = new ArrayList<>();
        productCategories.add(productCategory);
        productCategories.add(productCategory2);
        productCategories.add(productCategory3);

        int effectNum = productCategoryDao.batchInsertProductCategory(productCategories);
        assertEquals(3, effectNum);


    }

    @Test
    public void CtestDeleteProductCategory() {
        Long shopId = 1L;
        List<ProductCategory> productCategoryList = productCategoryDao.queryProductCategoryList(shopId);
        for (ProductCategory pc : productCategoryList) {
            if ("测试分类1".equals(pc.getProductCategoryName()) || "测试分类2".equals(pc.getProductCategoryName())) {
                int effectNum = productCategoryDao.deleteProductCategory(pc.getProductCategoryId(), shopId);
            }
        }
    }

}
