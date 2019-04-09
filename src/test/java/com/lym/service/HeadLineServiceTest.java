package com.lym.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @ClassName HeadLineServiceTest
 * @Description TODO
 * @Author lyming
 * @Date 2019/4/5 9:50 PM
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class HeadLineServiceTest   {

    @Autowired
    private HeadLineService headLineService;

    @Test
    public void quertHeadLine() {
        try {
            headLineService.getHeadLineList(null);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
