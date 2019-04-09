package com.lym.dao;

import com.lym.entity.PersonInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * @ClassName PersonInfoDaoTest
 * @Author lyming
 * @Date 2019/3/31 23:21
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class PersonInfoDaoTest   {
    @Autowired
    private PersonInfoDao personInfoDao;

    @Test
    public void testAInsertPersonInfo() throws Exception {
        PersonInfo personInfo = new PersonInfo();
        personInfo.setName("我爱你");
        personInfo.setGender("女");
        personInfo.setCreateTime(new Date());
        personInfo.setLastEditTime(new Date());
        personInfo.setEnableStatus(1);
        int effectedNum = personInfoDao.insertPersonInfo(personInfo);
        assertEquals(1, effectedNum);
    }


    @Test
    public void testQueryPersonInfoById() {
        long id = 1L;
        PersonInfo personInfo = personInfoDao.queryPersonInfoById(id);
        System.out.println(personInfo.getName());
    }

}
