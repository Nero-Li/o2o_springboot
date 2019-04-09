package com.lym.dao;

import com.lym.entity.ProductImg;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @ClassName ProductImgDaoTest
 * @Description TODO
 * @Author lyming
 * @Date 2019/1/23 4:01 PM
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductImgDaoTest   {

    @Autowired
    private ProductImgDao productImgDao;

    @Test
    public void testBatchInsertProductImg() {
        ProductImg productImg1 = new ProductImg();
        productImg1.setImgAddr("图片1");
        productImg1.setImgDesc("测试图片1");
        productImg1.setPriority(1);
        productImg1.setCreateTime(new Date());
        productImg1.setProductId(2L);
        ProductImg productImg2 = new ProductImg();
        productImg2.setImgAddr("图片2");
        productImg2.setPriority(1);
        productImg2.setCreateTime(new Date());
        productImg2.setProductId(2L);
        List<ProductImg> productImgList = new ArrayList<ProductImg>();
        productImgList.add(productImg1);
        productImgList.add(productImg2);
        int effectedNum = productImgDao.batchInsertProductImg(productImgList);
        assertEquals(2, effectedNum);
    }

    @Test
    public void testDeleteProductImgByProductId() {
        long productId = 2;
        int effectiveNum = productImgDao.deleteProductImgByProductId(productId);
        System.out.println(effectiveNum);
    }
}
