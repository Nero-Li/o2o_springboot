package com.lym.dao;

import com.lym.entity.Area;
import com.lym.entity.PersonInfo;
import com.lym.entity.Shop;
import com.lym.entity.ShopCategory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;
@RunWith(SpringRunner.class)
@SpringBootTest
public class ShopDaoTest   {


    private Logger logger = LoggerFactory.getLogger(ShopDaoTest.class);

    @Autowired
    private ShopDao shopDao;

    @Test
    public void testInsertShop() {
        Shop shop = new Shop();
        PersonInfo owner = new PersonInfo();
        Area area = new Area();
        ShopCategory shopCategory = new ShopCategory();
        owner.setUserId(1L);
        area.setAreaId(2);
        shopCategory.setShopCategoryId(1L);
        shop.setOwner(owner);
        shop.setArea(area);
        shop.setShopCategory(shopCategory);
        shop.setShopName("测试的店铺");
        shop.setShopDesc("testDesc");
        shop.setPhone("testPhone");
        shop.setShopAddr("testAddr");
        shop.setShopImg("testImg");
        shop.setPriority(1);
        shop.setCreateTime(new Date());
        shop.setEnableStatus(1);
        shop.setAdvice("审核中");
        shopDao.insertShop(shop);

    }

    @Test
    public void testUpdateShop() {
        Shop shop = new Shop();
        PersonInfo owner = new PersonInfo();
        Area area = new Area();
        ShopCategory shopCategory = new ShopCategory();
        owner.setUserId(1L);
        area.setAreaId(2);
        shop.setShopId(1L);
        shopCategory.setShopCategoryId(1L);
        shop.setOwner(owner);
        shop.setArea(area);
        shop.setShopCategory(shopCategory);
        shop.setShopName("测试的店铺3");
        shop.setShopDesc("testDesc");
        shop.setPhone("testPhone");
        shop.setShopAddr("testAddr");
        shop.setShopImg("testImg");
        shop.setPriority(1);
        shop.setCreateTime(new Date());
        shop.setEnableStatus(1);
        shop.setAdvice("审核中");
        shopDao.updateShop(shop);
    }

    @Test
    public void testQueryByShopId() {
        logger.info("=========start========");
        Shop shop = shopDao.queryByShopId(17L);
        logger.info("=========end========");

    }

    @Test
    public void testQueryShopListAndCount() {
        Shop shopCondition = new Shop();
        ShopCategory childCategory = new ShopCategory();
        ShopCategory parentCategory = new ShopCategory();
        parentCategory.setShopCategoryId(12L);
        childCategory.setParent(parentCategory);
        PersonInfo owner = new PersonInfo();
        owner.setUserId(8L);
        shopCondition.setOwner(owner);
        ShopCategory shopCategory = new ShopCategory();
        shopCategory.setShopCategoryId(2L);
        shopCondition.setShopCategory(childCategory);
        List<Shop> shopList = shopDao.queryShopList(shopCondition, 0, 10);
        int count = shopDao.queryShopCount(shopCondition);
        System.out.println("当前店铺列表数量:" + shopList.size() + "===店铺总数:" + count);
    }

}
