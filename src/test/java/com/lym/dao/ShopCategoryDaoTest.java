package com.lym.dao;

import com.lym.entity.ShopCategory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @ClassName ShopCategoryDaoTest
 * @Author lyming
 * @Date 2019/1/8 23:49
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class ShopCategoryDaoTest   {

    @Autowired
    private ShopCategoryDao shopCategoryDao;

    @Test
    public void testShopCategoryDao() {
        ShopCategory shopCategory = new ShopCategory();
        ShopCategory shopCategoryParent = new ShopCategory();
        shopCategoryParent.setShopCategoryId(1L);
        shopCategory.setParent(shopCategoryParent);
        List<ShopCategory> shopCategoryList = shopCategoryDao.queryShopCategory(shopCategory);
        System.out.println(shopCategoryList);
    }
}
