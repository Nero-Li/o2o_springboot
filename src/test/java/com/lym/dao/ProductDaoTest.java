package com.lym.dao;

import com.lym.entity.Product;
import com.lym.entity.ProductCategory;
import com.lym.entity.Shop;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @ClassName ProductDaoTest
 * @Description TODO
 * @Author lyming
 * @Date 2019/1/23 3:13 PM
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductDaoTest   {

    @Autowired
    private ProductDao productDao;

    @Test
    public void testAddProduct() {
        Shop shop1 = new Shop();
        shop1.setShopId(1L);
        Shop shop2 = new Shop();
        shop2.setShopId(2L);
        ProductCategory pc1 = new ProductCategory();
        pc1.setProductCategoryId(3L);
        ProductCategory pc2 = new ProductCategory();
        pc2.setProductCategoryId(3L);
        ProductCategory pc3 = new ProductCategory();
        pc3.setProductCategoryId(4L);
        Product product1 = new Product();
        product1.setProductName("测试1");
        product1.setProductDesc("测试Desc1");
        product1.setImgAddr("test1");
        product1.setPriority(0);
        product1.setEnableStatus(1);
        product1.setCreateTime(new Date());
        product1.setLastEditTime(new Date());
        product1.setShop(shop1);
        product1.setProductCategory(pc1);
        Product product2 = new Product();
        product2.setProductName("测试2");
        product2.setProductDesc("测试Desc2");
        product2.setImgAddr("test2");
        product2.setPriority(0);
        product2.setEnableStatus(0);
        product2.setCreateTime(new Date());
        product2.setLastEditTime(new Date());
        product2.setShop(shop1);
        product2.setProductCategory(pc2);
        Product product3 = new Product();
        product3.setProductName("测试3");
        product3.setProductDesc("测试Desc3");
        product3.setImgAddr("test3");
        product3.setPriority(0);
        product3.setEnableStatus(1);
        product3.setCreateTime(new Date());
        product3.setLastEditTime(new Date());
        product3.setShop(shop2);
        product3.setProductCategory(pc3);
        int effectedNum = productDao.insertProduct(product1);
        assertEquals(1, effectedNum);
        effectedNum = productDao.insertProduct(product2);
        assertEquals(1, effectedNum);
        effectedNum = productDao.insertProduct(product3);
        assertEquals(1, effectedNum);
    }

    @Test
    public void testGetProductById() {
        Product product = productDao.getProductById(7L);
        System.out.println(product.getProductDesc());
    }

    @Test
    public void testUpdateProduct() {
        Product product = new Product();
        ProductCategory pc = new ProductCategory();
        Shop shop = new Shop();
        shop.setShopId(1L);
        pc.setProductCategoryId(5L);
        product.setProductId(7L);
        product.setShop(shop);
        product.setProductCategory(pc);
        product.setProductName("第一个产品");
        int effctiveNum = productDao.updateProduct(product);
        assertEquals(1, effctiveNum);
    }

    @Test
    public void testQueryProductList(){
        Product productCondition = new Product();
        //分页查询
        List<Product> productList = productDao.queryProductList(productCondition,0,10);
        System.out.println(productList.size());
        System.out.println(productDao.queryProductCount(productCondition));
        productCondition.setProductName("正式");
        productList = productDao.queryProductList(productCondition,0,10);
        System.out.println(productList.size());
        System.out.println(productDao.queryProductCount(productCondition));
    }

    @Test
    public void testProductCategoryToNull(){
        //将productCategoryId为3的商品类别下面的商品的商品类别置为null
        int effectiveNum = productDao.updateProductCategoryToNull(3L);
        System.out.println("================="+effectiveNum+"==========");
    }
}
