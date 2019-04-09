package com.lym.dao;

import com.lym.entity.LocalAuth;
import com.lym.entity.PersonInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

/**
 * @ClassName LocalAuthDaoTest
 * @Author lyming
 * @Date 2019/4/7 13:23
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class LocalAuthDaoTest   {

    @Autowired
    private LocalAuthDao localAuthDao;

    public static final String username = "testusername";
    public static final String password = "testpassword";

    @Test
    public void testInsertLocalAuth() {
        //新增一条平台账号
        LocalAuth localAuth = new LocalAuth();
        PersonInfo personInfo = new PersonInfo();
        personInfo.setUserId(1L);
        //给平台账号绑定用户信息
        localAuth.setPersonInfo(personInfo);
        //设置上用户名和密码
        localAuth.setUsername(username);
        localAuth.setPassword(password);
        localAuth.setCreateTime(new Date());
        localAuthDao.insertLocalAuth(localAuth);
    }

    @Test
    public void testQueryLoacalByNameAndPwd() {
        LocalAuth localAuth = localAuthDao.queryLocalByUserNameAndPwd(username, password);
        System.out.println(localAuth.getCreateTime());
    }

    @Test
    public void testQueryLocalAuthByUserId() {
        LocalAuth localAuth = localAuthDao.queryLocalByUserId(1L);
        System.out.println(localAuth.getCreateTime());
    }

    @Test
    public void testUpdateLocalAuth() {
        Date date = new Date();
        localAuthDao.updateLocalAuth(1L, username, password, "passwordnew", date);
        LocalAuth localAuth = localAuthDao.queryLocalByUserId(1L);
        System.out.println(localAuth.getPassword());

    }
}
