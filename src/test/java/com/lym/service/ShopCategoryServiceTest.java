package com.lym.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @ClassName ShopCategoryServiceTest
 * @Description TODO
 * @Author lyming
 * @Date 2019/4/5 9:56 PM
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class ShopCategoryServiceTest   {

    @Autowired
    private ShopCategoryService shopCategoryService;

    @Test
    public void queryShopCategory() {
        shopCategoryService.getShopCategoryList(null);
    }
}
