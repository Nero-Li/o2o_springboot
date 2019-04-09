package com.lym.service;

import com.lym.dto.LocalAuthExecution;
import com.lym.entity.LocalAuth;
import com.lym.entity.PersonInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @ClassName LocalAuthServiceTest
 * @Author lyming
 * @Date 2019/4/7 15:03
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class LocalAuthServiceTest   {

    @Autowired
    private LocalAuthService localAuthService;

    @Test
    public void testBindLoaclAuth() {
        //新增一条平台信息
        LocalAuth localAuth = new LocalAuth();
        PersonInfo personInfo = new PersonInfo();
        String username = "testusername";
        String password = "testpassword";
        //给平台账号设置上用户信息
        //给用户设置上用户id,表明是具体某个用户创建的账号
        personInfo.setUserId(1L);
        //给平台账号设置用户信息,表明是与那个用户绑定
        localAuth.setPersonInfo(personInfo);
        //设置账号
        localAuth.setUsername(username);
        //设置密码
        localAuth.setPassword(password);
        //绑定账号
        LocalAuthExecution le = localAuthService.bindLocalAuth(localAuth);
        System.out.println(le.getStateInfo());
        localAuth = localAuthService.getLocalAuthByUserId(personInfo.getUserId());
        System.out.println("username:" + localAuth.getUsername());
        System.out.println("password:" + localAuth.getPassword());
    }

    @Test
    public void testModifyLoaclAuth() {
        //设置账号信息
        long userId = 1L;
        String username = "testusername";
        String password = "testpassword";
        String newpassword = "newpassword";
        //修改该账号对应的密码
        LocalAuthExecution le = localAuthService.modifyLocalAuth(userId, username, password, newpassword);
        LocalAuth localAuth = localAuthService.getLocalAuthByUserNameAndPwd(username, newpassword);
        System.out.println(localAuth.getUsername());
    }
}
