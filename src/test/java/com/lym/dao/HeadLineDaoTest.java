package com.lym.dao;

import com.lym.entity.HeadLine;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @ClassName HeadLineDaoTest
 * @Author lyming
 * @Date 2019/2/15 17:00
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class HeadLineDaoTest   {

    @Autowired
    private HeadLineDao headLineDao;

    @Test
    public void testQueryHeadLine(){
        List<HeadLine> headLineList = headLineDao.queryHeadLine(new HeadLine());
        assertEquals(1, headLineList.size());
    }
}
