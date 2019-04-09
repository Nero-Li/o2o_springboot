package com.lym;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * test
 * @ClassName Hello
 * @Author lyming
 * @Date 2019/4/9 12:01
 **/
@RestController
public class Hello {

    @RequestMapping(value = "/hello",method = RequestMethod.GET)
    private String hello(){
        return "Hello SpringBoot";
    }
}
